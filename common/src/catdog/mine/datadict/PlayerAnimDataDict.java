package catdog.mine.datadict;

import catdog.mine.AnimationData;

/**
 * 플레이어 애니메이션 데이터
 * @author CATDOG
 *
 */
public class PlayerAnimDataDict extends DataDictionary<AnimationData> {
	/**
	 * 서있을 때
	 */
	public static final String ID_STAND = "stand";

	/**
	 * 리스트 파일
	 */
	public static final String datalistfile = "data/animation/player_animations.txt";
	
	@Override
	public void loadFile(String id, String filename) {
		data.put(id, new AnimationData(filename));
	}

	@Override
	public void Load() {
		this.Load(datalistfile);
	}
}
