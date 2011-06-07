package catdog.mine.specialblock;

import java.util.Collection;

import catdog.mine.Item.Interaction;
import catdog.mine.monster.Mob;

public class Magnet implements Interaction {
	
	private static float MULTIPLIER = 0.7f;

	@Override
	public void boundIn(Mob mob, int blockX, int blockY, float delta) {
		mob.velocity.x = mob.WALK_SPEED * MULTIPLIER;
	}
	
	@Override
	public void boundOut(Mob mob, int blockX, int blockY, float delta) {
		;
	}

}
