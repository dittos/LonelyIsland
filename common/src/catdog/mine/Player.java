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

	public boolean canPutBlock(int x, int y) {
		// 플레이어와 아래쪽에 있는 블럭 좌표
		int blocky_bottom = (int)Math.floor(position.y);
		// 플레이어 위쪽에 있는 블럭 좌표
		int blocky_top = (int) Math.floor(position.y + hitbox.height);
		// 플레이어가 향한 방향, 바로 앞에 있는 블럭
		
		int blockx1 = (int)Math.floor(position.x + hitbox.width);
		int blockx2 = (int)Math.floor(position.x);
		
		return!((x == blockx1 && y == blocky_bottom) ||
				(x == blockx2 && y == blocky_bottom) ||
				(x == blockx1 && y == blocky_top) ||
				(x == blockx2 && y == blocky_top));
	}

}
