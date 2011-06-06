package catdog.mine;

import java.util.ArrayList;
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
	private Data data;
	
	
	/**
	 * �ִϸ��̼� ������ Ŭ����
	 * @author ReB
	 *
	 */
	public class Data {
		/**
		 * �� ������ �� ����
		 * @author ReB
		 *
		 */
		public class Frame {
			/**
			 *  �����Ӹ��� �ؽ���
			 */
			private Texture texture;
			/** 
			 * ������ ���ӽð�
			 */
			private float time;
			
			public Texture getTexture() {
				return texture;
			}

			public float getTime() {
				return time;
			}

			
			public Frame(Texture texture, float time) {
				this.texture = texture;
				this.time = time;
			}
		}
		
		/**
		 * ������ ����Ʈ
		 */
		private ArrayList<Frame> framelist;
		
		/**
		 * ������ ��ȣ
		 */
		private int framenum = 0;
		/**
		 * �ִϸ��̼��� �����ϴ���
		 */
		private boolean loop = false;
		
		/**
		 * ���� ������ ���ϱ�
		 * @return Frame ���۷���
		 */
		public Frame getCurrentFrame() {
			return framelist.get(framenum);
		}
		/**
		 * ���� ���������� �����ϱ�
		 * @return ���� ���������� �Ѿ���� ����, true�� ����
		 */
		public boolean nextFrame() {
			// �ִϸ��̼��� �����ٸ� �ƹ� �ϵ� ���� �ʰ� false
			if(isAnimationEnd())
				return false;
			
			// ���� ������, true����
			framenum = (framenum + 1) % framelist.size();
			return true;
		}
		
		/**
		 * �ִϸ��̼��� �������� üũ
		 * @return �������� true, �����ִϸ��̼��̰ų� ������ �ʾҴٸ� false
		 */
		public boolean isAnimationEnd() {
			return (!loop && framenum == framelist.size()-1);
		}
		
		/**
		 * ������ �߰��ϱ�
		 * @param frame : Frame ������Ʈ
		 */
		public void addFrame(Frame frame) {
			framelist.add(frame);
		}
	}
	
	public Animation(Animation.Data data) {
		this.data = data;
	}
	
	public void render(float delta, SpriteBatch sprbatch, Vector2 pos) {
		// ���� ������ ��������
		Data.Frame frame = data.getCurrentFrame();
		// ���� ������ ���
		sprbatch.draw(frame.getTexture(), pos.x, pos.y);
		
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
