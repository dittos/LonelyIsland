package catdog.mine.monster;

import catdog.mine.AnimationDB;
import catdog.mine.Block;
import catdog.mine.Life;
import catdog.mine.Player;
import catdog.mine.World;

public class Destructor extends Mob {
	
	public static final float maxLife = 1.5f;
	
	/**
	 * �� �ı� �ӵ� ����
	 */
	public static final float destroyFactor = 0.5f;
	

	public Destructor(World world, Player player) {
		super(world, player);
		// TODO Auto-generated constructor stub
	}
	
	public void loadAnimationData() {
		// TODO : ���� �ִϸ��̼� �ε��ϱ� (���� Ŭ���� ������� �ʰ�)
			standAni = AnimationDB.get("destructor_stand");
			walkAni = standAni;
			climbAni = standAni;
		super.loadAnimationData();
	}
	
	public void update(float delta) {
		boolean processed = false;
		
		// �÷��̾�� x�ప�� �ٸ��� ������⿡ ���� ���θ��� �ִٸ� �� �μ���
		if(getPlayer().position.x != this.position.x
				&& blockInPath(delta * 3)) {
			Block top = getWorld().getBlock((int)position.x + (getDirection() == Life.RIGHT? 1 : -1),
											(int)position.y + 1);
			Block bottom = getWorld().getBlock((int)position.x + (getDirection() == Life.RIGHT? 1 : -1),
											(int)position.y);
			// ���� ������
			if(top != null && top.isAlive()) {
				this.dig(top, delta * destroyFactor);
				processed = true;
			} else if(bottom != null && bottom.isAlive()) {
				this.dig(bottom, delta * destroyFactor);
				processed = true;
			}
			// �÷��̾ �ڽź��� �Ʒ��� ������
		} else {
			if(getPlayer().position.y < this.position.y){
				// �Ʒ��ʿ� ���� �ִ��� üũ
				Block below = getWorld().getBlock((int)position.x, (int)position.y-1);
				// �Ʒ��ʿ� ���� �ִٸ� 
				if(below != null && below.isAlive()) {
					this.dig(below, delta * destroyFactor);
					processed = true;
				}
				// �÷��̾ �ڽź��� ���� ������
			} else if(getPlayer().position.y > this.position.y){
				// ���� ���� �ִ��� üũ
				Block above = getWorld().getBlock((int)position.x, (int)position.y+2);
				// �Ʒ��ʿ� ���� �ִٸ� 
				if(above!= null && above.isAlive()) {
					this.dig(above, delta * destroyFactor);
					processed = true;
				}
			}
		}
		if(!processed)
			// �ٸ� ó���� ������ ���� ������Ʈ ó����
			super.update(delta);
	}
	
}
