package catdog.mine;

public class Game extends com.badlogic.gdx.Game {
	
	@Override
	public void create() {
		GameScreen gameScreen = new GameScreen(this);
		setScreen(gameScreen);
		
	}

}
