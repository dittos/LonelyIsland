package catdog.mine;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class TextureDict {
	
	private static HashMap<String, Texture> dict = new HashMap<String, Texture>();
	
	public static Texture load(String path) {
		if (!dict.containsKey(path))
			dict.put(path, new Texture(path));
		return dict.get(path);
	}
	
}
