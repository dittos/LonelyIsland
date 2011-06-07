package catdog.mine.monster;

import catdog.mine.AnimationDB;
import catdog.mine.Block;
import catdog.mine.Life;
import catdog.mine.Player;
import catdog.mine.World;

public class Destructor extends Mob {
	
	public static final float maxLife = 1.5f;
	
	/**
	 * 블럭 파괴 속도 비율
	 */
	public static final float destroyFactor = 0.5f;
	

	public Destructor(World world, Player player) {
		super(world, player);
		// TODO Auto-generated constructor stub
	}
	
	public void loadAnimationData() {
		// TODO : 개별 애니메이션 로드하기 (상위 클래스 사용하지 않고)
			standAni = AnimationDB.get("destructor_stand");
			walkAni = standAni;
			climbAni = standAni;
		super.loadAnimationData();
	}
	
	public void update(float delta) {
		boolean processed = false;
		
		// 플레이어와 x축값이 다르고 진행방향에 벽이 가로막고 있다면 벽 부수기
		if(getPlayer().position.x != this.position.x
				&& blockInPath(delta * 3)) {
			Block top = getWorld().getBlock((int)position.x + (getDirection() == Life.RIGHT? 1 : -1),
											(int)position.y + 1);
			Block bottom = getWorld().getBlock((int)position.x + (getDirection() == Life.RIGHT? 1 : -1),
											(int)position.y);
			// 위쪽 블럭부터
			if(top != null && top.isAlive()) {
				this.dig(top, delta * destroyFactor);
				processed = true;
			} else if(bottom != null && bottom.isAlive()) {
				this.dig(bottom, delta * destroyFactor);
				processed = true;
			}
			// 플레이어가 자신보다 아래에 있으면
		} else {
			if(getPlayer().position.y < this.position.y){
				// 아래쪽에 블럭이 있는지 체크
				Block below = getWorld().getBlock((int)position.x, (int)position.y-1);
				// 아래쪽에 블럭이 있다면 
				if(below != null && below.isAlive()) {
					this.dig(below, delta * destroyFactor);
					processed = true;
				}
				// 플레이어가 자신보다 위에 있으면
			} else if(getPlayer().position.y > this.position.y){
				// 위에 블럭이 있는지 체크
				Block above = getWorld().getBlock((int)position.x, (int)position.y+2);
				// 아래쪽에 블럭이 있다면 
				if(above!= null && above.isAlive()) {
					this.dig(above, delta * destroyFactor);
					processed = true;
				}
			}
		}
		if(!processed)
			// 다른 처리가 없으면 상위 오브젝트 처리를
			super.update(delta);
	}
	
}
