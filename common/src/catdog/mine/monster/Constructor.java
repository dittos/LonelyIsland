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
		
		// walk 상태일 때
		if(getState() == Life.STATE_WALK) {
			// 자기 앞쪽 바로 아래에 블럭을 놓을 수 있는 경우, 그 위치 x값이 플레이어가 있는 x값과 다르다면
			int newx = (int)this.position.x + (getDirection() == RIGHT? 1 : -1);
			int newy = (int)this.position.y - 1;
			
			if(newx != (int)getPlayer().position.x && this.canPutBlock(newx, newy))
				getWorld().putBlock(newx, newy, new Block(placeItem));
			
			
		}
	}
}
