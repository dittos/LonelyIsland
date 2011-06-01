package catdog.mine;

public class Block {
	/**
	 * 블럭 파괴 시간 (초)
	 */
	private float destroyTime = 1;
	
	/**
	 * 블럭 파괴까지 남은 시간 (초)
	 */
	private float timeLeft = destroyTime;

	/**
	 * delta초 동안 파임
	 * @param delta 파인 시간
	 */
	public void digged(float delta) {
		timeLeft -= delta;
		if (timeLeft <= 0)
			destroy();
	}
	
	/**
	 * 파괴
	 */
	public void destroy() {
		// TODO: World에 알려서 사라지기
	}
	
	/**
	 * 파괴 비율
	 * @return 파괴 비율 (0부터 1 사이)
	 */
	public float getDestructionRatio() {
		return 1 - Math.max(timeLeft, 0) / destroyTime;
	}
}
