package catdog.mine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * �ִϸ��̼� Ŭ����
 * @author ReB
 *
 */
public class Animation {
	/**
	 * �ִϸ��̼� ���� �ð�
	 */
	private float time;
	
	/**
	 * �ִϸ��̼� ������
	 */
	private AnimationData data;
	
	
	
	
	public Animation(AnimationData data) {
		this.data = data;
	}
	
	public void render(SpriteBatch sprbatch, Vector2 pos, boolean horizontalFlip, boolean verticalFlip) {
		render(sprbatch, pos, horizontalFlip, verticalFlip, 1f);
	}
	
	public void render(SpriteBatch sprbatch, Vector2 pos, boolean horizontalFlip, boolean verticalFlip, float alpha) {
		// ���� ������ ��������
		AnimationData.Frame frame = data.getCurrentFrame();
		Texture texture = frame.getTexture();
		
		// ���� ������ ���
		sprbatch.setColor(1f, 1f, 1f, alpha);
		sprbatch.draw(texture, pos.x, pos.y, texture.getWidth(), texture.getHeight(),
				0, 0, texture.getWidth(), texture.getHeight(), horizontalFlip, verticalFlip);
	}
	
	public void update(float delta) {
		// ���� ������ ��������
		AnimationData.Frame frame = data.getCurrentFrame();
		
		// �ִϸ��̼��� ������ �ʾҴٸ�
		if(!data.isAnimationEnd()) {
			// ����ð� �����ϱ�
			time += delta;
			
			// ���� ������ ���ӽð��� �Ѿ��ٸ�
			if(time >= frame.getTime()) {
				// ���ӽð���ŭ time���� ���� ���� ����������
				time -= frame.getTime();
				
				data.nextFrame();
			}
		}
	}
}
