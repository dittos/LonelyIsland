package catdog.mine;

import catdog.mine.datadict.PlayerAnimDataDict;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Life {
	// ���� ��ü ���۷���
	private World world;
	public Inventory inventory;
	
	// ����
	public Vector2 position = new Vector2();
	private Vector2 velocity = new Vector2(0, 0);
	private Vector2 destPos = new Vector2();
	
	private boolean direction = LEFT;
	private int state = STATE_STAND;
	private boolean hasDest = false;
	
	// ���
	public static final boolean LEFT = false;
	public static final boolean RIGHT = true;

	/**
	 * �ȴ� �ӵ� (����: ����/��)
	 */
	private static final int WALK_SPEED = 10;
	
	private static final int CLIMB_SPEED = 5;
	/**
	 * �߷� ��� (����: ����/��)
	 */
	private static final int GRAVITY = 40;
	
	/**
	 * ���� �ʱ� �ӵ� (����: ����/��)
	 */
	private static final int JUMP_SPEED = 11;
	
	/**
	 * ������ x�� �ӵ� (����: ����/��)
	 */
	private static final int JUMP_XSPEED = 3;
	
	/**
	 * �������� ������ ������ ó���� �Ÿ��� (����: ����)
	 */
	private static final float ARRIVE_THRES = 0.1f;
	
	/**
	 * �浹����
	 */
	private static final Rectangle hitbox = new Rectangle(-0.3f, -1.8f, 0.6f, 1.8f);
	
	private static final int STATE_STAND = 0;
	private static final int STATE_WALK = 1;
	private static final int STATE_FALL = 2;
	private static final int STATE_CLIMB = 3;
	private static final int STATE_JUMP = 4;
	
	/**
	 * ������
	 * @param world �� ��ü
	 */
	public Life(World world) {
		//playerTex = new Texture(Gdx.files.internal("data/player.png"));
		spriteBatch = new SpriteBatch();
		inventory = new Inventory();
		this.world = world;
		
		// �ִϸ��̼� �ʱ�ȭ
		playerAnim = new Animation(playeranimdata.getReference(PlayerAnimDataDict.ID_STAND));
	}
	
	/**
	 * �̵��� �õ��Ѵ�.
	 * @param newPos ������ ��ġ (��ġ�� ��)
	 */
	public void requestMove(Vector2 newPos) {
		// ��ġ�� ��ġ�� ĳ���� �߾��� �ǵ��� �ɾ��.
		walkTo(new Vector2(newPos.x - hitbox.width / 2f, newPos.y));
	}
	
	/**
	 * �ð��� ���� ���¸� ������Ʈ�Ѵ�.
	 * @param delta ������ �ð�
	 */
	public void update(float delta) {
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
				// ������ ������ ���ٸ� �̵�
				position.x += velocity.x * delta;
			else {
				// ������ ������ �ִٸ�
				// ���� ���̿� ������ �ְ�, �� ������ ���ٸ� ����
				float fx = position.x + hitbox.width / 2 + ((velocity.x > 0)? 1 : -1);
				if(world.getBlock(new Vector2(fx, position.y)) != null
						&& world.getBlock(new Vector2(fx, position.y + 1)) == null
						&& world.getBlock(new Vector2(fx, position.y+2))== null){
					 jump();
				} 
				else if(world.getBlock(new Vector2(position.x+1,position.y+1)) !=null ||
						world.getBlock(new Vector2(position.x-1,position.y+1))!=null)
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
			// ���ӵ� ���
			velocity.y -= GRAVITY * delta;
			// y������ �̵�
			position.y += velocity.y * delta;
			// ������ ������ �� �ִٸ� x�����ε� �̵�
			if(!blockInPath(velocity.x * delta))
				position.x += velocity.x * delta;
			// �������� ���� �� ���� ��� ���ٸ� ��� �Ȱ�
			if(velocity.y < 0 && hasStandingBlock()) {
				velocity.y = 0;
				position.y = (float) Math.ceil(position.y);
				// ���� ������ ���� �������� �� �������� ���� ���̶�� ����
				// (���� �� �Ҿ����� �������� �����ϱ� ����)
				if(Math.abs(position.x - destPos.x) < 1f)
					stand();
				else
					walkTo(destPos);
			}
			break;
			
		case STATE_CLIMB:
			float fx = position.x + hitbox.width / 2 + ((velocity.x > 0)? 1 : -1);
			position.y += velocity.y*delta;
			if(!blockInPath(velocity.x * delta))
				{
				position.x += velocity.x * delta;	
				velocity.y = 0;
				walkTo(destPos);	
				}
			if(world.getBlock(new Vector2(position.x, position.y+1)) != null
					//&& world.getBlock(new Vector2(fx, position.y + 2)) == null
					) {
				//jump();
				velocity.y = 0;
				stand();
				//walkTo(destPos);
			} 	
			else if(world.getBlock(new Vector2(position.x, position.y+2)) != null)
			{
				velocity.y=0;	
			}
		}
		
		// �ִϸ��̼� ������Ʈ
		playerAnim.update(delta);
	}
	
	/**
	 * �ڸ��� ���� ����.
	 */
	public void stand() {
		changestate(STATE_STAND);
		hasDest = false;
		velocity.set(0, 0);
		position.y = (float) Math.ceil(position.y);
	}
	
	/**
	 * �ش� ��ġ�� �̵��ϰ� ���� (�� ��ǥ������)
	 * ���� : �� ��ǥ�� ������Ʈ�� ���� �ϴ��� �������� �ϹǷ� ��ġ�� ������ �ٸ� 
	 * @param newPos
	 */
	private void walkTo(Vector2 newPos) {
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
	 * ������ �Ѵ�.
	 */
	public void jump() {
		changestate(STATE_JUMP);
		velocity.y = JUMP_SPEED;
		velocity.x = (velocity.x > 0)? JUMP_XSPEED : -JUMP_XSPEED;
	}
	
	/**
	 * ���� state �ٲٰ� �׿� �´� �ִϸ��̼� �ֱ�
	 * @param state
	 */
	private void changestate(int state) {
		this.state = state;
		
		switch(state) {
		case STATE_WALK:
			// �ȱ�
			playerAnim = new Animation(playeranimdata.getReference(PlayerAnimDataDict.ID_WALK));
			break;
		case STATE_CLIMB:
			playerAnim = new Animation(playeranimdata.getReference(PlayerAnimDataDict.ID_CLIMB));
			//��°ɷ� �ٲ�ߵ�.
			break;
		case STATE_STAND:
		default:
			// �⺻ : ���ֱ�
			playerAnim = new Animation(playeranimdata.getReference(PlayerAnimDataDict.ID_STAND));
		}
	}
	
	/**
	 * ��������.
	 */
	public void climb() {
		changestate(STATE_CLIMB);
		velocity.y = CLIMB_SPEED;
	}
	
	/**
	 * ������ ��� ���ֳ�?
	 * @return ������ false, ������ true
	 */
	private boolean hasStandingBlock() {
		int below = (int)Math.ceil(position.y - 1);
		return world.getBlock((int) Math.floor(position.x + hitbox.width), below) != null
				|| world.getBlock((int) Math.floor(position.x), below) != null;
	}
	
	/**
	 * �÷��̾ ���ϴ� ���⿡ ������ �ִ��� (�浹üũ)
	 * @param xmovedist x�࿡�� ������ �Ÿ�
	 * @return ������ true, ������ false
	 */
	private boolean blockInPath(float xmovedist) {
		// �÷��̾�� �Ʒ��ʿ� �ִ� ���� ��ǥ
		int blocky_bottom = (int)Math.floor(position.y);
		// �÷��̾� ���ʿ� �ִ� ���� ��ǥ
		int blocky_top = (int) Math.floor(position.y + hitbox.height);
		// �÷��̾ ���� ����, �ٷ� �տ� �ִ� ����
		
		int blockx1 = (int)Math.floor(position.x + xmovedist + hitbox.width);
		int blockx2 = (int)Math.floor(position.x + xmovedist);
		
		
		// �ش� ��ġ�� ������ ������ true
		return world.getBlock(blockx1, blocky_bottom) != null 
				|| world.getBlock(blockx1, blocky_top) != null
				|| world.getBlock(blockx2, blocky_bottom) != null
				|| world.getBlock(blockx2, blocky_top) != null;
	}
	
	/**
	 * �̵� ���� �� �������� �����ߴ��� Ȯ��
	 * @return ���������� true, �ƴϸ� false
	 */
	private boolean arrived() {
		// TODO: y��ǥ Ȯ��
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
		if (!block.isAlive())
			inventory.addItem(block.getItem());
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}
	
	  
		public void canPutBlock() {
		
	}

	// �׸��� ���� �ڵ�
	//private Texture playerTex;
	private Animation playerAnim;
	private SpriteBatch spriteBatch;
	
	public void render(Viewport viewport) {
		Vector2 screenPos = viewport.toScreen(position);
		spriteBatch.begin();
		playerAnim.render(spriteBatch, screenPos, direction == RIGHT, false);
		//spriteBatch.draw(playerTex, screenPos.x, screenPos.y, playerTex.getWidth(), playerTex.getHeight(),
		//		0, 0, playerTex.getWidth(), playerTex.getHeight(), direction == RIGHT, false);
		spriteBatch.end();
	}
	
	/**
	 * �÷��̾� �ִϸ��̼� ������
	 */
	private static PlayerAnimDataDict playeranimdata;
	/**
	 * ������ �ε��ϱ�
	 */
	public static void loadData() {
		playeranimdata = new PlayerAnimDataDict();
		playeranimdata.Load();
	}
}