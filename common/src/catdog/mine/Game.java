package catdog.mine;

public class Game extends com.badlogic.gdx.Game {
	public static final float epsilon = 0.01f;			// 부동소수점 값 비교시 오차범위

	@Override
	public void create() {
		GameScreen gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

}
