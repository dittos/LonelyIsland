package catdog.mine;

public class Block {
	
	/**
	 * 블럭 파괴까지 남은 시간 (초)
	 */
	private float timeLeft;
	
	/**
	 * 아이템 객체
	 */
	private Item item;
	
	private boolean alive = true;

	/**
	 * delta초 동안 파임
	 * @param delta 파인 시간
	 */
	public void digged(float delta) {
		if (alive) {
			timeLeft -= delta;
			if (timeLeft <= 0)
				destroy();
		}
	}
	
	/**
	 * 파괴
	 */
	public void destroy() {
		// TODO: World에 알려서 사라지기
		alive = false;
	}
	
	/**
	 * 파괴 비율
	 * @return 파괴 비율 (0부터 1 사이)
	 */
	public float getDestructionRatio() {
		return 1 - Math.max(timeLeft, 0) / item.getDestroytime();
	}
	
	/**
	 * 블럭이 파괴되었는가?
	 * @return 아직 살아있으면 true, 아니면 false
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * 부서졌을 때 튀어나오는 아이템을 돌려준다.
	 * @return 블럭에 귀속된 Item 객체
	 */
	public Item getItem() {
		return item;
	}
	
	public Block(Item item) {
		this.item = item;
		
		this.timeLeft = item.getDestroytime();
	}
}
