package catdog.mine;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

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
	private Data data;
	
	
	/**
	 * 애니메이션 데이터 클래스
	 * @author ReB
	 *
	 */
	public class Data {
		/**
		 * 각 프레임 당 정보
		 * @author ReB
		 *
		 */
		public class Frame {
			/**
			 *  프레임마다 텍스쳐
			 */
			private Texture texture;
			/** 
			 * 프레임 지속시간
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
		 * 프레임 리스트
		 */
		private ArrayList<Frame> framelist;
		
		/**
		 * 프레임 번호
		 */
		private int framenum = 0;
		/**
		 * 애니메이션이 루프하는지
		 */
		private boolean loop = false;
		
		/**
		 * 현재 프레임 구하기
		 * @return Frame 레퍼런스
		 */
		public Frame getCurrentFrame() {
			return framelist.get(framenum);
		}
		/**
		 * 다음 프레임으로 진행하기
		 * @return 다음 프레임으로 넘어갔는지 여부, true는 성공
		 */
		public boolean nextFrame() {
			// 애니메이션이 끝났다면 아무 일도 하지 않고 false
			if(isAnimationEnd())
				return false;
			
			// 다음 프레임, true리턴
			framenum = (framenum + 1) % framelist.size();
			return true;
		}
		
		/**
		 * 애니메이션이 끝났는지 체크
		 * @return 끝났으면 true, 루프애니메이션이거나 끝나지 않았다면 false
		 */
		public boolean isAnimationEnd() {
			return (!loop && framenum == framelist.size()-1);
		}
		
		/**
		 * 프레임 추가하기
		 * @param frame : Frame 오브젝트
		 */
		private void addFrame(Frame frame) {
			framelist.add(frame);
		}
		
		/** 
		 * 파일에서 데이터 읽어오기
		 * @param filename : 파일 이름(경로)
		 */
		public void loadFromFile(String filename) {
			// 라인 단위로 끊어서 파일 읽어오기
			String[] data = Gdx.files.internal(filename).readString()
						.split(System.getProperty("line.separator"));
			
			// 라인당 처리
			for(int i = 0; i < data.length; i++) {
				// 한 라인은 텍스쳐 경로[탭]프레임시간(float)으로 나뉘어짐
				// 잘 나눈 다음 Frame을 생성하여 추가하기
				String [] linespl = data[i].split("\t");
				addFrame(new Frame(new Texture(linespl[0]), Float.valueOf(linespl[1])));
			}
		}
	}
	
	public Animation(Animation.Data data) {
		this.data = data;
	}
	
	public void render(float delta, SpriteBatch sprbatch, Vector2 pos) {
		// 현재 프레임 가져오기
		Data.Frame frame = data.getCurrentFrame();
		// 현재 프레임 출력
		sprbatch.draw(frame.getTexture(), pos.x, pos.y);
		
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
