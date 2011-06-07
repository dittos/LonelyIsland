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
	private float deltaMul = 1f;
	/** 
	 * 최대 체력
	 */
	public static final float maxLife = 1f;
	/**
	 * 현재 체력
	 */
	private float life = maxLife;

	public float getDeltaMul()
	{
		return deltaMul;
	}

	public void setDeltaMul(float deltaMul)
	{
		this.deltaMul = deltaMul;
	}

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
		delta *= deltaMul;
		super.update(delta);
		if (nticks++ % 30 == 0) // 일정 시간마다 플레이어 쪽으로 향하게 함
			walkTo(player.position);
		
		if(Math.abs(player.position.x - position.x)+Math.abs(player.position.y - position.y)<1)
		{
			//캐릭터가 죽었슴다--;
			player.killed();
		}
	}
	
	/**
	 * 체력 구하기
	 * @return 현재 체력
	 */
	public float getLife() {
		return life;
	}
	
	/**
	 * 살아있는지 체크
	 * @return 살아있으면 true
	 */
	public boolean isAlive() {
		return life > 0;
	}
	
	/**
	 * 데미지 주기
	 * @param damage 데미지 양
	 */
	public void hit(float damage) {
		life -= damage;
		if(!isAlive())
			onDie();
	}
	
	protected void onDie() {
		// TODO : 죽을 때 추가처리
	}
}
