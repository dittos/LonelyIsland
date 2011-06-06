package catdog.mine.datadict;

import catdog.mine.AnimationData;

/**
 * ���� �ִϸ��̼� ������
 * @author ReB
 *
 */
public class MonsterAnimDataDict extends DataDictionary<AnimationData> {
	private static final String listfile = "data/animation/monster_animations.txt"; 

	@Override
	public void Load() {
		Load(listfile);
	}

	@Override
	public void loadFile(String id, String filename) {
		data.put(id, new AnimationData(filename));
	}
	
	/**
	 * ���� �̸��� �ִϸ��̼� �̸��� �´� �ִϸ��̼� ��������
	 * @param mon ���� �̸�
	 * @param anim �ִϸ��̼� �̸�
	 * @return AnimationData ���۷���
	 */
	public AnimationData getReference(String mon, String anim) {
		return getReference(mon + "_" + anim);
	}
}
