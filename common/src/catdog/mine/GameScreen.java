package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	
	private Viewport viewport;
	private World world;
	private Player player;

	@Override
	public void render(float delta) {
		if (Gdx.input.isTouched()) {
			// ��ġ �̺�Ʈ�� ȭ�� ���� ���� (0, 0)
			Vector2 touchPos = viewport.fromScreen(
					new Vector2(Gdx.input.getX(), viewport.screenHeight - Gdx.input.getY())
			);
			
			Block block = world.getBlock(touchPos);
			if (block != null && player.isNear(touchPos))
				block.digged(delta);
			else
				if (Gdx.input.justTouched())
					player.requestMove(touchPos);
		}
		
		player.update(delta);
		viewport.focusOn(player.position);
		
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		world.render(viewport);
		player.render(viewport);
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
