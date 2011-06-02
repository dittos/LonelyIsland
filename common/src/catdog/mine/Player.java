package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	public Vector2 position = new Vector2();
	private Vector2 movepos = new Vector2();
	private Vector2 velocity = new Vector2(0, 0);
	
	private Texture playerTex;
	private SpriteBatch spriteBatch;
	
	private World world;
	
	public Inventory inventory;
	
	private boolean direction = LEFT;
	
	public static final boolean LEFT = false;
	public static final boolean RIGHT = true;
	
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
	
	/**
	 * 충돌범위
	 */
	private static final Rectangle hitbox = new Rectangle(-0.45f, -1.8f, 0.9f, 1.8f);
	
	private static final int STATE_STAND = 0;
	private static final int STATE_WALK = 1;
	private static final int STATE_FALL = 2;
	private static final int STATE_CLIMB = 3;
	private static final int STATE_JUMP = 4;
	private int state = STATE_STAND;
	
	/**
	 * 생성자
	 * @param world 맵 객체
	 */
	public Player(World world) {
		playerTex = new Texture(Gdx.files.internal("data/player.png"));
		spriteBatch = new SpriteBatch();
		inventory = new Inventory();
		this.world = world;
	}
	
	/**
	 * 이동을 시도한다.
	 * @param newPos 목적지 위치
	 */
	public void requestMove(Vector2 newPos) {
		movepos.set(newPos);
		if (position.x < movepos.x) // ->
			direction = RIGHT;
		else // <-
			direction = LEFT;
		velocity.x = (direction == LEFT ? -1 : +1) * WALK_SPEED;
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
			break;
			
		case STATE_WALK:
			if (!hasStandingBlock())
				state = STATE_FALL;
			//else if(collision())
			//	state = STATE_STAND;
			else if (arrived())
				stand();
			else {
				// 막히는 블럭이 없다면 이동
				if(!blockInPath(velocity.x * delta))
					position.x += velocity.x * delta;
				// 막히는 블럭이 있다면 일단 STAND
				else stand();
			}
			break;
			
		case STATE_FALL:
			velocity.y -= GRAVITY * delta;
			position.y += velocity.y * delta;
			
			if (hasStandingBlock())
				stand();
			break;
			
		case STATE_CLIMB:
			break;
			
		}
	}
	
	/**
	 * 자리에 멈춰 선다.
	 */
	public void stand() {
		state = STATE_STAND;
		velocity.set(0, 0);
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
		int below = (int)Math.ceil(position.y - 1);
		return world.getBlock((int) Math.floor(position.x + 0.5 + hitbox.width / 2f), below) != null
				|| world.getBlock((int) Math.floor(position.x + 0.5 - hitbox.width / 2f), below) != null;
	}
	
	/*private boolean collision(){
		int direction;
		if (position.x < movepos.x) // ->
			direction = +1;
		else // <-
			direction = -1;
		int right = (int)Math.ceil(position.x);
		int left = (int)Math.ceil(position.x-1);
		int y = (int)Math.ceil(position.y);
		if ((world.getBlock(right,y) != null || world.getBlock(right,y+1) != null)  && direction ==1)//
			return true;
		else if((world.getBlock(left,y) != null || world.getBlock(left,y+1) != null)  && direction == -1)//
			return true;
		else
			return false;
		
	}*/
	
	/**
	 * 플레이어가 향하는 방향에 블럭이 있는지 (충돌체크)
	 * @param xmovedist x축에서 움직일 거리
	 * @return 있으면 true, 없으면 false
	 */
	private boolean blockInPath(float xmovedist) {
		// 플레이어와 아래쪽에 있는 블럭 좌표
		int blocky_bottom = (int)Math.ceil(position.y);
		// 플레이어 위쪽에 있는 블럭 좌표
		int blocky_top = (int) Math.floor(position.y + hitbox.height);
		// 플레이어가 향한 방향, 바로 앞에 있는 블럭
		int blockx;
		
		if(xmovedist >= 0)
			blockx = (int)Math.floor(position.x + 0.5 + xmovedist + hitbox.width / 2f);
		else
			blockx = (int)Math.floor(position.x + 0.5 + xmovedist - hitbox.width /2f);
		
		
		// 해당 위치에 블럭이 있으면 true
		return world.getBlock(blockx, blocky_bottom) != null 
				|| world.getBlock(blockx, blocky_top) != null;
	}
	
	/**
	 * 이동 중일 때 목적지에 도착했는지 확인
	 * @return 도착했으면 true, 아니면 false
	 */
	private boolean arrived() {
		// TODO: y좌표 확인
		return Math.abs(position.x - movepos.x) < Game.epsilon;
	}
	
	public void render(Viewport viewport) {
		Vector2 screenPos = viewport.toScreen(position);
		spriteBatch.begin();
		spriteBatch.draw(playerTex, screenPos.x, screenPos.y, playerTex.getWidth(), playerTex.getHeight(),
				0, 0, playerTex.getWidth(), playerTex.getHeight(), direction == RIGHT, false);
		spriteBatch.end();
	}
	
	public boolean isNear(Vector2 touchPos){
		if((Math.abs(position.x+ 0.5 - touchPos.x)<1.5) && Math.abs(position.y +1 - touchPos.y)<2)
			return true;
		else if(Math.abs(position.x+ 0.5 - touchPos.x)<1.5 && Math.abs(position.y +1 - touchPos.y)<1)
			return true;
		else
			return false;
	}

	public void dig(Block block, float delta) {
		block.digged(delta);
		if (!block.isAlive())
			inventory.addItem(block.getItem());
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}
	
}
