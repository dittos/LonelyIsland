package catdog.mine.specialblock;

import java.util.Collection;

import catdog.mine.Block;
import catdog.mine.Item.Interaction;
import catdog.mine.monster.Mob;

public class Hotstone implements Interaction{

	private static final float DAMAGE = 0.3f;
	
	@Override
	public void boundIn(Mob mob, Collection<Mob> allMobs, Block targetBlock, int blockX, int blockY, float delta) {
		int mobs = 0, range = targetBlock.getItem().getInteractDist();
		
		for(Mob other: allMobs)
		{
			if(Math.abs(other.position.x - blockX) + Math.abs(other.position.y - blockY) <= range)
				++ mobs;
		}
		
		mob.hit(DAMAGE * delta / mobs);
	}
	
	@Override
	public void boundOut(Mob mob, Collection<Mob> allMobs, Block targetBlock, int blockX, int blockY, float delta) {
	}
	
}