package catdog.mine.specialblock;

import catdog.mine.Item.Interaction;
import catdog.mine.monster.Mob;

public class Electower implements Interaction{

	private float damage = 0.8f;
	
	@Override
	public void boundIn(Mob mob, int blockX, int blockY, float delta) {
	mob.hit(damage*delta);
	}
	@Override
	public void boundOut(Mob mob, int blockX, int blockY, float delta) {
		;
	}
	
}