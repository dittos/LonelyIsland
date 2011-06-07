package catdog.mine.monster;

import catdog.mine.AnimationDB;
import catdog.mine.Life;
import catdog.mine.Player;
import catdog.mine.World;

public class Mob extends Life {
	
	private Player player;
	protected Player getPlayer() {
		return player;
	}
	private int nticks = 0;
	/** 
	 * �ִ� ü��
	 */
	public static final float maxLife = 1f;
	/**
	 * ���� ü��
	 */
	private float life = maxLife;

	public Mob(World world, Player player) {
		super(world);
		this.player = player;
		WALK_SPEED = 2;
	}

	@Override
	protected void loadAnimationData() {
		standAni = AnimationDB.get("zombie_stand");
		walkAni = standAni;
		climbAni = standAni;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		if (nticks++ % 30 == 0) // ���� �ð����� �÷��̾� ������ ���ϰ� ��
			walkTo(player.position);
	}
	
	/**
	 * ü�� ���ϱ�
	 * @return ���� ü��
	 */
	public float getLife() {
		return life;
	}
	
	/**
	 * ����ִ��� üũ
	 * @return ��������� true
	 */
	public boolean isAlive() {
		return life > 0;
	}
	
	/**
	 * ������ �ֱ�
	 * @param damage ������ ��
	 */
	public void hit(float damage) {
		life -= damage;
		if(!isAlive())
			onDie();
	}
	
	protected void onDie() {
		// TODO : ���� �� �߰�ó��
	}
}
