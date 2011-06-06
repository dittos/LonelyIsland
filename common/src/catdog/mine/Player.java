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
		// �÷��̾�� �Ʒ��ʿ� �ִ� �� ��ǥ
		int blocky_bottom = (int)Math.floor(position.y);
		// �÷��̾� ���ʿ� �ִ� �� ��ǥ
		int blocky_top = (int) Math.floor(position.y + hitbox.height);
		// �÷��̾ ���� ����, �ٷ� �տ� �ִ� ��
		
		int blockx1 = (int)Math.floor(position.x + hitbox.width);
		int blockx2 = (int)Math.floor(position.x);
		
		return!((x == blockx1 && y == blocky_bottom) ||
				(x == blockx2 && y == blocky_bottom) ||
				(x == blockx1 && y == blocky_top) ||
				(x == blockx2 && y == blocky_top));
	}

}
