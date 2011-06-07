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
	 * ���� ĳ��
	 * @param tree ���� ������Ʈ
	 * @param delta ĳ�� �ð�
	 */
	public void chopTree(Tree tree, float delta) {
		tree.chopped(delta);
		// ������ �׾��ٸ� ���� �������� �κ��丮�� �߰�
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
