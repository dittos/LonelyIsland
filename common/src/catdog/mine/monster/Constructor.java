package catdog.mine.monster;

import catdog.mine.Block;
import catdog.mine.Item;
import catdog.mine.ItemDB;
import catdog.mine.Life;
import catdog.mine.Player;
import catdog.mine.World;

public class Constructor extends Mob {
	public static final float maxLife = 2f;
	public static Item placeItem = ItemDB.getItem(1);

	public Constructor(World world, Player player) {
		super(world, player);
		// TODO Auto-generated constructor stub
	}
	
	public void loadAnimationData() {
		// TODO : ��ü �ִϸ��̼� ����ϱ�
		super.loadAnimationData();
		
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
