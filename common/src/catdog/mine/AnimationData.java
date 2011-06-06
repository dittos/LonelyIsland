package catdog.mine;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * �ִϸ��̼� ������ Ŭ����
 * @author ReB
 *
 */
public class AnimationData {
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
	private void addFrame(Frame frame) {
		framelist.add(frame);
	}
	
	/** 
	 * ���Ͽ��� ������ �о����
	 * @param filename : ���� �̸�(���)
	 */
	public void loadFromFile(String filename) {
		// ���� ������ ��� ���� �о����
		String[] data = Gdx.files.internal(filename).readString()
					.split("\r\n");
		
		// ù ���� : ���� ����
		loop = Boolean.valueOf(data[0]);
		
		// ���δ� ó��
		for(int i = 1; i < data.length; i++) {
			// �� ������ �ؽ��� ���[��]�����ӽð�(float)���� ��������
			// �� ���� ���� Frame�� �����Ͽ� �߰��ϱ�
			String [] linespl = data[i].split("\t");
			addFrame(new Frame(new Texture(linespl[0]), Float.valueOf(linespl[1])));
		}
	}
	
	public AnimationData() {
		framelist = new ArrayList<Frame>();
	}
	public AnimationData(String filename) {
		this();
		
		loadFromFile(filename);
	}
}