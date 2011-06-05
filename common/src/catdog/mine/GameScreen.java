package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	
	private Viewport viewport;
	private World world;
	private Player player;
	private InventoryView inventoryView;

	@Override
	public void render(float delta) {
		if (Gdx.input.isTouched()) {
			// 터치 이벤트는 화면 왼쪽 위가 (0, 0)
			Vector2 touchPos = viewport.fromScreen(
					new Vector2(Gdx.input.getX(), viewport.screenHeight - Gdx.input.getY())
			);
			
			// 터치한 곳에 따라 캐릭터 시선 방향을 정한다.
			if (touchPos.x < player.position.x)
				player.setDirection(Player.LEFT);
			else
				player.setDirection(Player.RIGHT);
			
			Block block = world.getBlock(touchPos);
			if (block != null) {
				if (player.isNear(touchPos))
					player.dig(block, delta);
				//else
					// TODO: 손이 안 닿아도 뭔가 파는 시늉을?
			}
			else
				if (Gdx.input.justTouched())
					player.requestMove(touchPos);
		}
		
		player.update(delta);
		viewport.focusOn(player.position);
		
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		world.render(viewport);
		player.render(viewport);
		inventoryView.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		world = new World();
		player = new Player(world);
		player.position.set(2, World.GROUND_ALTITUDE);
		inventoryView = new InventoryView(player.inventory);
		viewport = new Viewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 32, 32);
		viewport.focusOn(player.position);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
