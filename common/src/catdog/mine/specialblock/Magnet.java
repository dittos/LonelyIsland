package catdog.mine.specialblock;

import java.util.Collection;

import catdog.mine.Item.Interaction;
import catdog.mine.Life;

public class Magnet implements Interaction {
	
	private static float MULTIPLIER = 0.7f;

	@Override
	public void interact(Collection<Life> mob, int blockX, int blockY) {
		for (Life life : mob) {
			life.velocity.x = life.WALK_SPEED * MULTIPLIER;
		}
	}

}
