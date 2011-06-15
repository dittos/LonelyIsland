package catdog.mine;

import java.util.HashMap;

public class AnimationDB {
	
	private static HashMap<String, AnimationData> data = new HashMap<String, AnimationData>();
	
	public static AnimationData get(String filename) {
		if (!data.containsKey(filename)) {
			AnimationData ani = new AnimationData("data/animation/" + filename + ".txt");
			data.put(filename, ani);
		}
		return data.get(filename);
	}
	
	public static void unloadAll() {
		data.clear();
	}
	
}
