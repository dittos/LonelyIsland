package catdog.mine.specialblock;

import catdog.mine.Item.Interaction;
import catdog.mine.monster.Mob;

public class Electower implements Interaction{

	private float damage = 1.0f;
	
	@Override
	public void boundIn(Mob mob, int blockX, int blockY, float delta) {
	mob.hit(damage);
	}
	@Override
	public void boundOut(Mob mob, int blockX, int blockY, float delta) {
		;
	}
	
}