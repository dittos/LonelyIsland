package catdog.mine;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class Desktop {
	public static void main(String[] argv) {
		new JoglApplication(new Game(), "Lonely Island", 533, 320, false);
	}
}
