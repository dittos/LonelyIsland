package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
	public Vector2 position;
	private Vector2 movepos;
	
	private Texture playerTex;
	private SpriteBatch spriteBatch;
	
	private World world;
	
	
	/**
	 * ������
	 * @param x �ʱ� ��ġ x��ǥ
	 * @param y �ʱ� ��ġ y��ǥ
	 */
	public Player(float x, float y, World world) {
		position = new Vector2(x, y);
		movepos = new Vector2(x, y);
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
	}
	
	public void render(Viewport viewport) {
		Vector2 screenPos = viewport.toScreen(position);
		spriteBatch.begin();
		spriteBatch.draw(playerTex, screenPos.x, screenPos.y);
		spriteBatch.end();
	}
	
	public void update(float delta) {
		// ������
	}
}
