package catdog.mine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * 애니메이션 클래스
 * @author ReB
 *
 */
public class Animation {
	/**
	 * 애니메이션 진행 시간
	 */
	private float time;
	
	/**
	 * 애니메이션 데이터
	 */
	private AnimationData data;
	
	
	
	
	public Animation(AnimationData data) {
		this.data = data;
	}
	
	public void render(SpriteBatch sprbatch, Vector2 pos, boolean horizontalFlip, boolean verticalFlip) {
		render(sprbatch, pos, horizontalFlip, verticalFlip, 1f);
	}
	
	public void render(SpriteBatch sprbatch, Vector2 pos, boolean horizontalFlip, boolean verticalFlip, float alpha) {
		// 현재 프레임 가져오기
		AnimationData.Frame frame = data.getCurrentFrame();
		Texture texture = frame.getTexture();
		
		// 현재 프레임 출력
		sprbatch.setColor(1f, 1f, 1f, alpha);
		sprbatch.draw(texture, pos.x, pos.y, texture.getWidth(), texture.getHeight(),
				0, 0, texture.getWidth(), texture.getHeight(), horizontalFlip, verticalFlip);
	}
	
	public void update(float delta) {
		// 현재 프레임 가져오기
		AnimationData.Frame frame = data.getCurrentFrame();
		
		// 애니메이션이 끝나지 않았다면
		if(!data.isAnimationEnd()) {
			// 진행시간 누적하기
			time += delta;
			
			// 현재 프레임 지속시간을 넘었다면
			if(time >= frame.getTime()) {
				// 지속시간만큼 time에서 빼고 다음 프레임으로
				time -= frame.getTime();
				
				data.nextFrame();
			}
		}
	}
}
