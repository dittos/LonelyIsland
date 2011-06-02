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
	 * �ȴ� �ӵ� (����: ��/��)
	 */
	private static final int WALK_SPEED = 10;
	
	/**
	 * �߷� ��� (����: ��/��)
	 */
	private static final int GRAVITY = 20;
	
	/**
	 * ���� �ʱ� �ӵ� (����: ��/��)
	 */
	private static final int JUMP_SPEED = 20;
	
	/**
	 * �浹����
	 */
	private static final Rectangle hitbox = new Rectangle(-0.45f, -1.8f, 0.9f, 1.8f);
	
	private static final int STATE_STAND = 0;
	private static final int STATE_WALK = 1;
	private static final int STATE_FALL = 2;
	private static final int STATE_CLIMB = 3;
	private static final int STATE_JUMP = 4;
	private int state = STATE_STAND;
	
	/**
	 * ������
	 * @param world �� ��ü
	 */
	public Player(World world) {
		playerTex = new Texture(Gdx.files.internal("data/player.png"));
		spriteBatch = new SpriteBatch();
		inventory = new Inventory();
		this.world = world;
	}
	
	/**
	 * �̵��� �õ��Ѵ�.
	 * @param newPos ������ ��ġ
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
	 * �ð��� ���� ���¸� ������Ʈ�Ѵ�.
	 * @param delta ������ �ð�
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
				// ������ ���� ���ٸ� �̵�
				if(!blockInPath(velocity.x * delta))
					position.x += velocity.x * delta;
				// ������ ���� �ִٸ� �ϴ� STAND
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
	 * �ڸ��� ���� ����.
	 */
	public void stand() {
		state = STATE_STAND;
		velocity.set(0, 0);
	}
	
	/**
	 * ������ �Ѵ�.
	 */
	public void jump() {
		velocity.y = JUMP_SPEED;
	}
	
	/**
	 * ��������.
	 */
	public void climb() {
		
	}
	
	/**
	 * ���� ��� ���ֳ�?
	 * @return ������ false, ������ true
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
	 * �÷��̾ ���ϴ� ���⿡ ���� �ִ��� (�浹üũ)
	 * @param xmovedist x�࿡�� ������ �Ÿ�
	 * @return ������ true, ������ false
	 */
	private boolean blockInPath(float xmovedist) {
		// �÷��̾�� �Ʒ��ʿ� �ִ� �� ��ǥ
		int blocky_bottom = (int)Math.ceil(position.y);
		// �÷��̾� ���ʿ� �ִ� �� ��ǥ
		int blocky_top = (int) Math.floor(position.y + hitbox.height);
		// �÷��̾ ���� ����, �ٷ� �տ� �ִ� ��
		int blockx;
		
		if(xmovedist >= 0)
			blockx = (int)Math.floor(position.x + 0.5 + xmovedist + hitbox.width / 2f);
		else
			blockx = (int)Math.floor(position.x + 0.5 + xmovedist - hitbox.width /2f);
		
		
		// �ش� ��ġ�� ���� ������ true
		return world.getBlock(blockx, blocky_bottom) != null 
				|| world.getBlock(blockx, blocky_top) != null;
	}
	
	/**
	 * �̵� ���� �� �������� �����ߴ��� Ȯ��
	 * @return ���������� true, �ƴϸ� false
	 */
	private boolean arrived() {
		// TODO: y��ǥ Ȯ��
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
