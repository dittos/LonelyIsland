package catdog.mine.monster;

import com.badlogic.gdx.math.Vector2;

import catdog.mine.Player;
import catdog.mine.World;

public class Ghost extends Mob {
	public static final float maxLife = 1f;
	
	/**
	 * 시간당 데미지 배율
	 */
	public static final float damagePerSecondFactor = 0.1f;
	/**
	 * 시간당 이동 거리
	 */
	public static final float speed = 2f;
	
	/**
	 * 상하로 움직이는 거리
	 */
	public static final float wavingheight = 0.2f;
	
	private Vector2 pseudoPos;
	private float totaltime = 0f;

	public Ghost(World world, Player player) {
		super(world, player);
		
		pseudoPos = position;
	}

	public void update(float delta) {
		//기본 처리 그런거 업ㅂ다 무조건 날아가기
		
		// TODO: Ghost도 느려지게?
		delta *= getDeltaMul();
		
		Vector2 movevector = new Vector2(getPlayer().position.x - this.pseudoPos.x, 
												getPlayer().position.y - this.pseudoPos.y).nor();
		
		this.pseudoPos.add(movevector.mul(speed * delta));
		
		this.position = pseudoPos.cpy();
		this.position.y += Math.sin(totaltime * Math.PI * 2 / 1.3f) * wavingheight;
		
		totaltime += delta;
		
		// 방향 설정
		setDirection(movevector.x >= 0? RIGHT : LEFT);
		
		// 체력 감소
		if(isAlive())
			hit(damagePerSecondFactor * delta);
	}
}
