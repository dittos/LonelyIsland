package catdog.mine;

public class Block {
	/**
	 * �� �ı� �ð� (��)
	 */
	private float destroyTime = 1;
	
	/**
	 * �� �ı����� ���� �ð� (��)
	 */
	private float timeLeft = destroyTime;
	
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
		return 1 - Math.max(timeLeft, 0) / destroyTime;
	}
	
	/**
	 * ���� �ı��Ǿ��°�?
	 * @return ���� ��������� true, �ƴϸ� false
	 */
	public boolean isAlive() {
		return alive;
	}
}
