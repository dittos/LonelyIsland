package catdog.mine;

import com.badlogic.gdx.math.Vector2;

/**
 * 화면에 표시되는 영역을 관리하는 클래스
 * 
 * 좌표계는 각 블럭이 1x1의 크기이고 맵 왼쪽 아래의 좌표가 (0, 0)인 것으로 한다.
 * 블럭과 블럭 사이의 위치는 (100.3, 50.12)와 같이 소숫점 단위로 나타내면 된다.
 * 
 * @author 김태호
 *
 */
public class Viewport {
	/**
	 * (0, 0)에 표시할 맵 상의 좌표 
	 */
	public float startX, startY;
	
	/**
	 * 화면 안에 나타나는 가로, 세로 블럭 수
	 */
	public float width, height;
	
	public int screenWidth, screenHeight;
	
	public int blockWidth, blockHeight;
	
	/**
	 * 생성자
	 * @param screenWidth 화면의 가로 크기 (px)
	 * @param screenHeight 화면의 세로 크기 (px)
	 * @param blockWidth 블럭의 가로 크기 (px)
	 * @param blockHeight 블럭의 세로 크기 (px)
	 */
	public Viewport(int screenWidth, int screenHeight, int blockWidth, int blockHeight) {
		width = screenWidth / blockWidth;
		height = screenHeight / blockHeight;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.blockWidth = blockWidth;
		this.blockHeight = blockHeight;
	}
	
	/**
	 * 맵 좌표계로 된 좌표를 화면 상의 좌표로 변환한다.
	 * @param mapPos 맵 좌표계의 좌표
	 * @return 화면 상의 좌표
	 */
	public Vector2 toScreen(Vector2 mapPos) {
		return new Vector2((mapPos.x - startX) * blockWidth, (mapPos.y - startY) * blockHeight);
	}
	
	/**
	 * 화면 상의 좌표를 맵 좌표계로 변환한다.
	 * @param screenPos 화면 상의 좌표
	 * @return 맵 좌표계로 변환된 좌표
	 */
	public Vector2 fromScreen(Vector2 screenPos) {
		return new Vector2(screenPos.x / blockWidth + startX, screenPos.y / blockHeight + startY);
	}
	
	/**
	 * 특정 위치로 초점을 이동한다.
	 * @param focus 화면 가운데로 올 좌표
	 */
	public void focusOn(Vector2 focus) {
		startX = focus.x - width / 2;
		startY = focus.y - height / 2;
	}
}
