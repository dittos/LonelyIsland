package catdog.mine.specialblock;

import java.util.Collection;

import catdog.mine.Item.Interaction;
import catdog.mine.monster.Mob;

public class Magnet implements Interaction {
	
	private static float MULTIPLIER = 0.7f;

	@Override
	public void interact(Collection<Mob> mob, int blockX, int blockY) {
		for (Mob life : mob) {
			life.velocity.x = life.WALK_SPEED * MULTIPLIER;
		}
	}

}
