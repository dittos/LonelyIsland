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
	
	private static final int STATE_STAND = 0;
	private static final int STATE_WALK = 1;
	private static final int STATE_FALL = 2;
	private static final int STATE_CLIMB = 3;
	private int state = STATE_STAND;
	
	/**
	 * ������
	 * @param world �� ��ü
	 */
	public Player(World world) {
		playerTex = new Texture(Gdx.files.internal("data/player.png"));
		spriteBatch = new SpriteBatch();
		this.world = world;
	}
	
	/**
	 * �̵��� �õ��Ѵ�.
	 * @param newPos ������ ��ġ
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
	 * �ð��� ���� ���¸� ������Ʈ�Ѵ�.
	 * @param delta ������ �ð�
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
		return true;
	}
	
	/**
	 * �̵� ���� �� �������� �����ߴ��� Ȯ��
	 * @return ���������� true, �ƴϸ� false
	 */
	private boolean arrived() {
		// TODO: y��ǥ Ȯ��
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
	 * �� �ı� ����
	 * @param block �� ��
	 */
	public void startDig(Block block) {
		// TODO: �� �� �ִ� ������ Ȯ��
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
