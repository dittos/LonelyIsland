package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen implements Screen {
	
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private int days;
	private Game game;
	
	public GameOverScreen(Game game, int days) {
		this.game = game;
		this.days = days;
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.justTouched()) {
			game.setScreen(new GameScreen(game));
			return;
		}
		
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		font.setColor(Color.RED);
		printCenter("GAME OVER", 250);
		font.setColor(Color.WHITE);
		printCenter("You've survived for " + days + " days", 200);
		printCenter("Touch the screen to retry...", 80);
		spriteBatch.end();
	}
	
	private void printCenter(String str, float y) {
		font.draw(spriteBatch, str, (Gdx.graphics.getWidth() - font.getBounds(str).width) / 2, y);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		font = new BitmapFont();
		spriteBatch = new SpriteBatch();
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
