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
	
	/**
	 * 나무 캐기
	 * @param tree 나무 오브젝트
	 * @param delta 캐는 시간
	 */
	public void chopTree(Tree tree, float delta) {
		tree.chopped(delta);
		// 나무가 죽었다면 나무 아이템을 인벤토리에 추가
		if(!tree.isAlive()) {
			inventory.addItem(ItemDB.getItem(9));
		}
	}
	public void dig(Block block, float delta) {
		super.dig(block, delta);
		if (!block.isAlive())
			inventory.addItem(block.getItem());
	}

}
