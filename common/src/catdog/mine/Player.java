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
	 * 생성자
	 * @param x 초기 위치 x좌표
	 * @param y 초기 위치 y좌표
	 */
	public Player(float x, float y, World world) {
		position = new Vector2(x, y);
		movepos = new Vector2(x, y);
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
	}
	
	public void render(Viewport viewport) {
		Vector2 screenPos = viewport.toScreen(position);
		spriteBatch.begin();
		spriteBatch.draw(playerTex, screenPos.x, screenPos.y);
		spriteBatch.end();
	}
	
	public void update(float delta) {
		// 움직임
	}
}
