package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
	public Vector2 position = new Vector2();
	private Vector2 movepos = new Vector2();
	private Vector2 velocity = new Vector2(0, 0);
	private Block digging = null;
	
	private Texture playerTex;
	private SpriteBatch spriteBatch;
	
	private World world;
	
	/**
	 * 걷는 속도 (단위: 블럭/초)
	 */
	private static final int WALK_SPEED = 10;
	
	/**
	 * 중력 상수 (단위: 블럭/초)
	 */
	private static final int GRAVITY = 20;
	
	/**
	 * 점프 초기 속도 (단위: 블럭/초)
	 */
	private static final int JUMP_SPEED = 20;
	
	private static final int STATE_STAND = 0;
	private static final int STATE_WALK = 1;
	private static final int STATE_FALL = 2;
	private static final int STATE_CLIMB = 3;
	private int state = STATE_STAND;
	
	/**
	 * 생성자
	 * @param world 맵 객체
	 */
	public Player(World world) {
		playerTex = new Texture(Gdx.files.internal("data/player.png"));
		spriteBatch = new SpriteBatch();
		this.world = world;
	}
	
	/**
	 * 이동을 시도한다.
	 * @param newPos 목적지 위치
	 */
	public void requestMove(Vector2 newPos) {
		movepos.set(newPos);
		int direction;
		if (position.x < movepos.x) // ->
			direction = +1;
		else // <-
			direction = -1;
		velocity.x = direction * WALK_SPEED;
		state = STATE_WALK;
	}
	
	/**
	 * 시간에 따라 상태를 업데이트한다.
	 * @param delta 지나간 시간
	 */
	public void update(float delta) {
		switch (state) {
		case STATE_STAND:
			if (!hasStandingBlock())
				state = STATE_FALL;
			
			if (digging != null)
				digging.digged(delta);
			break;
			
		case STATE_WALK:
			if (!hasStandingBlock())
				state = STATE_FALL;
			else if (arrived())
				state = STATE_STAND;
			else
				position.x += velocity.x * delta;
			break;
			
		case STATE_FALL:
			velocity.y -= GRAVITY * delta;
			position.y += velocity.y * delta;
			break;
			
		case STATE_CLIMB:
			break;
		}
	}
	
	/**
	 * 점프를 한다.
	 */
	public void jump() {
		velocity.y = JUMP_SPEED;
	}
	
	/**
	 * 기어오른다.
	 */
	public void climb() {
		
	}
	
	/**
	 * 블럭을 딛고 서있나?
	 * @return 없으면 false, 있으면 true
	 */
	private boolean hasStandingBlock() {
		return true;
	}
	
	/**
	 * 이동 중일 때 목적지에 도착했는지 확인
	 * @return 도착했으면 true, 아니면 false
	 */
	private boolean arrived() {
		// TODO: y좌표 확인
		final float thres = 0.01f;
		return Math.abs(position.x - movepos.x) < thres;
	}
	
	public void render(Viewport viewport) {
		Vector2 screenPos = viewport.toScreen(position);
		spriteBatch.begin();
		spriteBatch.draw(playerTex, screenPos.x, screenPos.y);
		spriteBatch.end();
	}

	/**
	 * 블럭 파기 시작
	 * @param block 팔 블럭
	 */
	public void startDig(Block block) {
		// TODO: 팔 수 있는 곳인지 확인
		digging = block;
	}
	
	public boolean isNear(Vector2 touchPos){
		if((Math.abs(position.x+ 0.5 - touchPos.x)<1.5) && Math.abs(position.y +1 - touchPos.y)<2)
			return true;
		else if(Math.abs(position.x+ 0.5 - touchPos.x)<1.5 && Math.abs(position.y +1 - touchPos.y)<1)
			return true;
		else
			return false;
	}
	
}
