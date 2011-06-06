package catdog.mine;

public class Player extends Life {

	public Player(World world) {
		super(world);
		WALK_SPEED = 10;
	}

	@Override
	protected void loadAnimationData() {
		standAni = AnimationDB.get("player_stand");
		walkAni = AnimationDB.get("player_walk");
		climbAni = AnimationDB.get("player_climb");
	}

}
