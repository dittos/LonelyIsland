package catdog.mine;

public class Mob extends Life {
	
	private Player player;
	private int nticks = 0;

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
		if (nticks++ % 30 == 0) // 일정 시간마다 플레이어 쪽으로 향하게 함
			requestMove(player.position);
	}
}
