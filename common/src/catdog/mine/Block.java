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

	/**
	 * delta�� ���� ����
	 * @param delta ���� �ð�
	 */
	public void digged(float delta) {
		timeLeft -= delta;
		if (timeLeft <= 0)
			destroy();
	}
	
	/**
	 * �ı�
	 */
	public void destroy() {
		// TODO: World�� �˷��� �������
	}
	
	/**
	 * �ı� ����
	 * @return �ı� ���� (0���� 1 ����)
	 */
	public float getDestructionRatio() {
		return 1 - Math.max(timeLeft, 0) / destroyTime;
	}
}
