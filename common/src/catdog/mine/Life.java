package catdog.mine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Life {
	// 관련 객체 레퍼런스
	private World world;
	protected World getWorld() {
		return world;
	}
	public Inventory inventory;
	
	// 상태
	public Vector2 position = new Vector2();
	public Vector2 velocity = new Vector2(0, 0);
	private Vector2 destPos = new Vector2();
	
	private boolean direction = LEFT;
	public boolean getDirection() {
		return direction;
	}
	
	private int state = STATE_STAND;
	public int getState() {
		return state;
	}
	private boolean hasDest = false;
	
	// 상수
	public static final boolean LEFT = false;
	public static final boolean RIGHT = true;

	/**
	 * 걷는 속도 (단위: 블럭/초)
	 */
	protected float WALK_SPEED = 1;
	
	private static final int CLIMB_SPEED = 5;
	/**
	 * 중력 상수 (단위: 블럭/초)
	 */
	private static final int GRAVITY = 40;
	
	/**
	 * 점프 초기 속도 (단위: 블럭/초)
	 */
	private static final int JUMP_SPEED = 11;
	
	/**
	 * 점프시 x축 속도 (단위: 블럭/초)
	 */
	private static final int JUMP_XSPEED = 3;
	
	/**
	 * 목적지에 도착한 것으로 처리할 거리값 (단위: 블럭)
	 */
	private static final float ARRIVE_THRES = 0.1f;
	
	/**
	 * 충돌범위
	 */
	protected Rectangle hitbox = new Rectangle(-0.3f, -1.8f, 0.6f, 1.8f);
	
	public static final int STATE_STAND = 0;
	public static final int STATE_WALK = 1;
	public static final int STATE_FALL = 2;
	public static final int STATE_CLIMB = 3;
	public static final int STATE_JUMP = 4;
	
	protected float lifeRatio = 1f;
	
	/**
	 * 생성자
	 * @param world 맵 객체
	 */
	public Life(World world) {
		//playerTex = new Texture(Gdx.files.internal("data/player.png"));
		spriteBatch = new SpriteBatch();
		inventory = new Inventory();
		this.world = world;
		
		loadAnimationData();
		
		// 애니메이션 초기화
		currentAni = new Animation(standAni);
	}
	
	/**
	 * 이동을 시도한다.
	 * @param newPos 목적지 위치 (터치한 곳)
	 */
	public void requestMove(Vector2 newPos) {
		// 터치한 위치가 캐릭터 중앙이 되도록 걸어간다.
		Vector2 dest = new Vector2(newPos.x - hitbox.width / 2f, newPos.y);
		if (state == STATE_CLIMB) {
			// 기어올라가는 중에는 목적지만 바꾼다.
			destPos.set(dest);
		} else {
			walkTo(dest);
		}
	}
	
	/**
	 * 시간에 따라 상태를 업데이트한다.
	 * @param delta 지나간 시간
	 */
	public void update(float delta) {
		float fx = position.x + hitbox.width + ((velocity.x > 0)? 1 : -1);
		switch (state) {
		
		case STATE_STAND:
			if (!hasStandingBlock())
				changestate(STATE_FALL);
			break;
		case STATE_WALK:
			if (!hasStandingBlock())
				changestate(STATE_FALL);
			else if (arrived())
				stand();
			else if(!blockInPath(velocity.x * delta))
				// 막히는 블럭이 없다면 이동
				position.x += velocity.x * delta;
			else {
				// 막히는 블럭이 있다면
				// 같은 높이에 블럭이 있고, 그 위에는 없다면 점프
				if(world.getBlock(new Vector2(fx, position.y)) != null
						&& world.getBlock(new Vector2(fx, position.y + 1)) == null
						&& world.getBlock(new Vector2(fx, position.y+2) )== null
						)
				{
					 jump();
				} 
				else if(world.getBlock(new Vector2(fx,position.y+1)) !=null)
					climb();
				else	
					stand();
			}
			break;
		case STATE_FALL:
			velocity.y -= GRAVITY * delta;
			position.y += velocity.y * delta;
			if (hasStandingBlock()) {
				if (hasDest) {
					stand();
					walkTo(destPos);
				} else
					stand();
			}
			break;
		case STATE_JUMP:
			// 가속도 계산
			velocity.y -= GRAVITY * delta;
			// y축으로 이동
			position.y += velocity.y * delta;
			// 앞으로 움직일 수 있다면 x축으로도 이동
			if(!blockInPath(velocity.x * delta))
				position.x += velocity.x * delta;
			// 떨어지는 중일 때 땅을 딛고 섰다면 계속 걷게
			if(velocity.y < 0 && hasStandingBlock()) {
				velocity.y = 0;
				position.y = (float) Math.ceil(position.y);
				// 만약 착지한 곳이 도착점과 얼마 떨어지지 않은 곳이라면 정지
				// (착지 후 불안정한 움직임을 보정하기 위함)
				if(Math.abs(position.x - destPos.x) < 1f)
					stand();
				else
					walkTo(destPos);
			}
			break;
		case STATE_CLIMB:
			position.y += velocity.y*delta;
			if(!blockInPath(velocity.x * delta)){
				//다 올라왔을때
				position.x += velocity.x * delta;	
				}
			if(world.getBlock(new Vector2(fx, position.y)) == null && world.getBlock(new Vector2(fx, position.y+1)) == null) {
				velocity.y = 0;
				walkTo(destPos);
			}//위에 블럭으로 막혔을때
			if(world.getBlock(new Vector2(position.x+0.2f,position.y+1)) != null
					|| world.getBlock(new Vector2(position.x+0.2f,position.y+2)) != null)
			{
				stand();
			}
		}
		// 애니메이션 업데이트
		currentAni.update(delta);
	}
	
	/**
	 * 자리에 멈춰 선다.
	 */
	public void stand() {
		changestate(STATE_STAND);
		hasDest = false;
		velocity.set(0, 0);
		position.y = (float) Math.ceil(position.y);
	}
	
	/**
	 * 해당 위치로 이동하게 명령 (맵 좌표상으로)
	 * 참고 : 맵 좌표는 오브젝트이 좌측 하단을 기준으로 하므로 터치한 곳과는 다름 
	 * @param newPos
	 */
	protected void walkTo(Vector2 newPos) {
		hasDest = true;
		destPos.set(newPos);
		if (position.x < destPos.x) // ->
			direction = RIGHT;
		else // <-
			direction = LEFT;
		velocity.x = (direction == LEFT ? -1 : +1) * WALK_SPEED;
		changestate(STATE_WALK);
	}
	
	/**
	 * 점프를 한다.
	 */
	public void jump() {
		changestate(STATE_JUMP);
		velocity.y = JUMP_SPEED;
		velocity.x = (velocity.x > 0)? JUMP_XSPEED : -JUMP_XSPEED;
	}
	
	/**
	 * 현재 state 바꾸고 그에 맞는 애니메이션 넣기
	 * @param state
	 */
	private void changestate(int state) {
		this.state = state;
		
		switch(state) {
		case STATE_WALK:
			// 걷기
			currentAni = new Animation(walkAni);
			break;
		case STATE_CLIMB:
			currentAni = new Animation(climbAni);
			//기는걸로 바꿔야됨.
			break;
		case STATE_STAND:
		default:
			// 기본 : 서있기
			currentAni = new Animation(standAni);
		}
	}
	/**
	 * 기어오른다.
	 */
	public void climb() {
		changestate(STATE_CLIMB);
		velocity.y = CLIMB_SPEED;
	}
	
	/**
	 * 블럭을 딛고 서있나?
	 * @return 없으면 false, 있으면 true
	 */
	protected boolean hasStandingBlock() {
		int below = (int)Math.ceil(position.y - 1);
		return world.getBlock((int) Math.floor(position.x + hitbox.width), below) != null
				|| world.getBlock((int) Math.floor(position.x), below) != null;
	}
	
	/**
	 * 플레이어가 향하는 방향에 블럭이 있는지 (충돌체크)
	 * @param xmovedist x축에서 움직일 거리
	 * @return 있으면 true, 없으면 false
	 */
	protected boolean blockInPath(float xmovedist) {
		// 플레이어와 아래쪽에 있는 블럭 좌표
		int blocky_bottom = (int)Math.floor(position.y);
		// 플레이어 위쪽에 있는 블럭 좌표
		int blocky_top = (int) Math.floor(position.y + hitbox.height);
		// 플레이어가 향한 방향, 바로 앞에 있는 블럭
		
		int blockx1 = (int)Math.floor(position.x + xmovedist + hitbox.width);
		int blockx2 = (int)Math.floor(position.x + xmovedist);
		
		// 해당 위치에 블럭이 있으면 true
		return world.getBlock(blockx1, blocky_bottom) != null 
				|| world.getBlock(blockx1, blocky_top) != null
				|| world.getBlock(blockx2, blocky_bottom) != null
				|| world.getBlock(blockx2, blocky_top) != null
				|| world.getBlock((int)(position.x+xmovedist+hitbox.width),(int)position.y+1) != null
				|| world.getBlock((int)position.x,(int)position.y+1) != null;
	}
	/**
	 * 이동 중일 때 목적지에 도착했는지 확인
	 * @return 도착했으면 true, 아니면 false
	 */
	protected boolean arrived() {
		// TODO: y좌표 확인
		return Math.abs(position.x - destPos.x) < ARRIVE_THRES;
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
	}

	protected void setDirection(boolean direction) {
		this.direction = direction;
	}

	// 그리기 관련 코드
	private Animation currentAni;
	private SpriteBatch spriteBatch;
	
	protected AnimationData standAni, walkAni, climbAni;
	
	protected abstract void loadAnimationData();
	
	public void render(Viewport viewport) {
		Vector2 screenPos = viewport.toScreen(position);
		spriteBatch.begin();
		currentAni.render(spriteBatch, screenPos, direction == RIGHT, false, 0.5f + lifeRatio * 0.5f);
		//spriteBatch.draw(playerTex, screenPos.x, screenPos.y, playerTex.getWidth(), playerTex.getHeight(),
		//		0, 0, playerTex.getWidth(), playerTex.getHeight(), direction == RIGHT, false);
		spriteBatch.end();
	}
	
	/**
	 * 오브젝트가 있는 위치를 고려해 블럭을 놓을 수 있는지 체크 
	 * @param x 블럭을 놓을 x좌표
	 * @param y 블럭을 놓을 y좌표
	 * @return 놓을 수 있으면 true
	 */
	public boolean canPutBlock(int x, int y) {
		// 플레이어와 아래쪽에 있는 블럭 좌표
		int blocky_bottom = (int)Math.floor(position.y);
		// 플레이어 위쪽에 있는 블럭 좌표
		int blocky_top = (int) Math.floor(position.y + hitbox.height);
		// 플레이어가 향한 방향, 바로 앞에 있는 블럭
		
		int blockx1 = (int)Math.floor(position.x + hitbox.width);
		int blockx2 = (int)Math.floor(position.x);
		
		return!((x == blockx1 && y == blocky_bottom) ||
				(x == blockx2 && y == blocky_bottom) ||
				(x == blockx1 && y == blocky_top) ||
				(x == blockx2 && y == blocky_top));
	}
}
