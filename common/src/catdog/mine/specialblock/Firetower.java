package catdog.mine.specialblock;

import java.util.Collection;

import catdog.mine.Block;
import catdog.mine.Item.Interaction;
import catdog.mine.monster.Mob;

public class Firetower implements Interaction{

	private float damage = 1.0f;
	
	@Override
	public void boundIn(Mob mob, Collection<Mob> allMobs, Block targetBlock, int blockX, int blockY, float delta) {
	mob.hit(damage*delta);
	}
	@Override
	public void boundOut(Mob mob, Collection<Mob> allMobs, Block targetBlock, int blockX, int blockY, float delta) {
		;
	}
	
}