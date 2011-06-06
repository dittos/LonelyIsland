package catdog.mine;

public class Block {
	
	/**
	 * �� �ı����� ���� �ð� (��)
	 */
	private float timeLeft;
	
	/**
	 * ������ ��ü
	 */
	private Item item;
	
	private boolean alive = true;

	/**
	 * delta�� ���� ����
	 * @param delta ���� �ð�
	 */
	public void digged(float delta) {
		if (alive) {
			timeLeft -= delta;
			if (timeLeft <= 0)
				destroy();
		}
	}
	
	/**
	 * �ı�
	 */
	public void destroy() {
		// TODO: World�� �˷��� �������
		alive = false;
	}
	
	/**
	 * �ı� ����
	 * @return �ı� ���� (0���� 1 ����)
	 */
	public float getDestructionRatio() {
		return 1 - Math.max(timeLeft, 0) / item.getDestroytime();
	}
	
	/**
	 * ���� �ı��Ǿ��°�?
	 * @return ���� ��������� true, �ƴϸ� false
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * �μ����� �� Ƣ����� �������� �����ش�.
	 * @return ���� �ͼӵ� Item ��ü
	 */
	public Item getItem() {
		return item;
	}
	
	public Block(Item item) {
		this.item = item;
		
		this.timeLeft = item.getDestroytime();
	}
}
