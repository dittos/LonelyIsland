package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class World {
	
	private Texture blockTex;
	private SpriteBatch spriteBatch;
	private Block[][] map;
	
	public static final int WIDTH = 100;
	public static final int HEIGHT = 50;
	public static final int GROUND_ALTITUDE = 25;
	
	public World() {
		blockTex = new Texture(Gdx.files.internal("data/block.png"));
		spriteBatch = new SpriteBatch();
		map = new Block[HEIGHT][WIDTH];
		initMap();
	}
	
	/**
	 * 맵 배치를 초기화한다.
	 */
	private void initMap() {
		for (int i = 0; i < GROUND_ALTITUDE; i++) {
			for (int j = 0; j < WIDTH; j++) {
				map[i][j] = new Block();
			}
		}
		
		// TODO: 이것은 임시 코드임. 계단 만들기 -_-
		for (int i = GROUND_ALTITUDE; i < HEIGHT; i++) {
			map[i][i - GROUND_ALTITUDE + 10] = new Block();
		}
	}

	public void render(Viewport viewport) {
		spriteBatch.begin();
		Vector2 pos = new Vector2();
		int x0, y0, x1, y1;
		x0 = Math.max(0, (int)viewport.startX);
		y0 = Math.max(0, (int)viewport.startY);
		x1 = Math.min(WIDTH - 1, (int)(viewport.startX + viewport.width));
		y1 = Math.min(HEIGHT - 1, (int)(viewport.startY + viewport.height));
		for (int i = y0; i <= y1; i++) {
			for (int j = x0; j <= x1; j++) {
				Block block = map[i][j]; 
				if (block != null) {
					pos.set(j, i);
					Vector2 screenPos = viewport.toScreen(pos);
					// 파괴 정도에 따라 투명도 조정
					spriteBatch.setColor(1, 1, 1, 1 - block.getDestructionRatio());
					spriteBatch.draw(blockTex, screenPos.x, screenPos.y);
				}
			}
		}
		spriteBatch.end();
	}

	/**
	 * 해당 위치의 블럭 구하기
	 * @param pos 좌표 벡터
	 * @return Block 오브젝트 (없을 경우 null)
	 */
	public Block getBlock(Vector2 pos) {
		return getBlock((int)pos.x, (int)pos.y);
	}
	
	/**
	 * 해당 위치의 블럭 구하기
	 * @param x x좌표
	 * @param y y좌표
	 * @return Block 오브젝트 (없을 경우 null)
	 */
	public Block getBlock(int x, int y) {
		if (0 <= x && x < WIDTH && 0 <= y && y < HEIGHT) {
			// 잘못된 인덱스가 아니면
			Block block = map[y][x];
			if (block != null && block.isAlive())
				return block;
		}
		return null;
	}
}
