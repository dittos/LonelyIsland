package catdog.mine.monster;

import catdog.mine.AnimationDB;
import catdog.mine.Block;
import catdog.mine.Item;
import catdog.mine.ItemDB;
import catdog.mine.Life;
import catdog.mine.Player;
import catdog.mine.World;

public class Constructor extends Mob {
	public static Item placeItem = ItemDB.getItem(1);

	public Constructor(World world, Player player) {
		super(world, player);
		// TODO Auto-generated constructor stub
		maxLife = 2f;
		WALK_SPEED = 0.8f;
	}
	
	public void loadAnimationData() {
		standAni = AnimationDB.get("constructor_stand");
		walkAni = standAni;
		climbAni = standAni;
	}
	
	public void update(float delta) {
		super.update(delta);
		
		// walk ������ ��
		if(getState() == Life.STATE_WALK) {
			// �ڱ� ���� �ٷ� �Ʒ��� ���� ���� �� �ִ� ���, �� ��ġ x���� �÷��̾ �ִ� x���� �ٸ��ٸ�
			int newx = (int)this.position.x + (getDirection() == RIGHT? 1 : -1);
			int newy = (int)this.position.y - 1;
			
			if(newx != (int)getPlayer().position.x && this.canPutBlock(newx, newy))
				getWorld().putBlock(newx, newy, new Block(placeItem));
		}
	}
}
