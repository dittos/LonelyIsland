package catdog.mine.datadict;

import catdog.mine.AnimationData;

/**
 * 몬스터 애니메이션 데이터
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
	 * 몬스터 이름과 애니메이션 이름에 맞는 애니메이션 가져오기
	 * @param mon 몬스터 이름
	 * @param anim 애니메이션 이름
	 * @return AnimationData 레퍼런스
	 */
	public AnimationData getReference(String mon, String anim) {
		return getReference(mon + "_" + anim);
	}
}
