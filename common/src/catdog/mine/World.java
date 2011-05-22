package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {
	
	private Texture blockTex;
	private SpriteBatch spriteBatch;
	private Block[][] map;
	
	private static final int WIDTH = 100;
	private static final int HEIGHT = 50;
	private static final int GROUND_ALTITUDE = 25;

	private int blockWidth, blockHeight;
	private Texture playerTex;
	private float playerX, playerY;
	
	public World() {
		playerTex = new Texture(Gdx.files.internal("data/player.png"));
		blockTex = new Texture(Gdx.files.internal("data/block.png"));
		blockWidth = blockTex.getWidth();
		blockHeight = blockTex.getHeight();
		spriteBatch = new SpriteBatch();
		map = new Block[HEIGHT][WIDTH];
		initMap();
		playerX = 0;
		playerY = GROUND_ALTITUDE;
	}
	
	private void initMap() {
		for (int i = 0; i < GROUND_ALTITUDE; i++) {
			for (int j = 0; j < WIDTH; j++) {
				map[i][j] = new Block();
			}
		}
		for (int i = GROUND_ALTITUDE; i < HEIGHT; i++) {
			map[i][i - GROUND_ALTITUDE] = new Block();
		}
	}

	public void render(float delta) {
		spriteBatch.begin();
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (map[i][j] != null)
					spriteBatch.draw(blockTex, j * blockWidth, (i - GROUND_ALTITUDE + 5) * blockHeight);
			}
		}
		spriteBatch.draw(playerTex, playerX * blockWidth, (playerY - GROUND_ALTITUDE + 5) * blockHeight);
		spriteBatch.end();
	}

}
