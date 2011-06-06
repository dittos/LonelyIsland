package catdog.mine.datadict;

import catdog.mine.AnimationData;

/**
 * �÷��̾� �ִϸ��̼� ������
 * @author CATDOG
 *
 */
public class PlayerAnimDataDict extends DataDictionary<AnimationData> {
	/**
	 * ������ ��
	 */
	public static final String ID_STAND = "stand";

	/**
	 * ����Ʈ ����
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
