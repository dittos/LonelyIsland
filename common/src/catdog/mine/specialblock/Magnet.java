package catdog.mine.specialblock;

import java.util.Collection;

import catdog.mine.Item.Interaction;
import catdog.mine.monster.Mob;

public class Magnet implements Interaction {
	// TODO: 테스트 수치. 릴리즈 전 수정 필요
	private static float MULTIPLIER = 0.1f;

	@Override
	public void boundIn(Mob mob, int blockX, int blockY, float delta) {
		mob.setDeltaMul(MULTIPLIER);
	}
	
	@Override
	public void boundOut(Mob mob, int blockX, int blockY, float delta) {
		mob.setDeltaMul(1f);
	}

}
