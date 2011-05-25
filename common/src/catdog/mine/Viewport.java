package catdog.mine;

import com.badlogic.gdx.math.Vector2;

/**
 * ȭ�鿡 ǥ�õǴ� ������ �����ϴ� Ŭ����
 * 
 * ��ǥ��� �� ���� 1x1�� ũ���̰� �� ���� �Ʒ��� ��ǥ�� (0, 0)�� ������ �Ѵ�.
 * ���� �� ������ ��ġ�� (100.3, 50.12)�� ���� �Ҽ��� ������ ��Ÿ���� �ȴ�.
 * 
 * @author ����ȣ
 *
 */
public class Viewport {
	/**
	 * (0, 0)�� ǥ���� �� ���� ��ǥ 
	 */
	public float startX, startY;
	
	/**
	 * ȭ�� �ȿ� ��Ÿ���� ����, ���� �� ��
	 */
	public float width, height;
	
	public int screenWidth, screenHeight;
	
	public int blockWidth, blockHeight;
	
	/**
	 * ������
	 * @param screenWidth ȭ���� ���� ũ�� (px)
	 * @param screenHeight ȭ���� ���� ũ�� (px)
	 * @param blockWidth ���� ���� ũ�� (px)
	 * @param blockHeight ���� ���� ũ�� (px)
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
	 * �� ��ǥ��� �� ��ǥ�� ȭ�� ���� ��ǥ�� ��ȯ�Ѵ�.
	 * @param mapPos �� ��ǥ���� ��ǥ
	 * @return ȭ�� ���� ��ǥ
	 */
	public Vector2 toScreen(Vector2 mapPos) {
		return new Vector2((mapPos.x - startX) * blockWidth, (mapPos.y - startY) * blockHeight);
	}
	
	/**
	 * ȭ�� ���� ��ǥ�� �� ��ǥ��� ��ȯ�Ѵ�.
	 * @param screenPos ȭ�� ���� ��ǥ
	 * @return �� ��ǥ��� ��ȯ�� ��ǥ
	 */
	public Vector2 fromScreen(Vector2 screenPos) {
		return new Vector2(screenPos.x / blockWidth + startX, screenPos.y / blockHeight + startY);
	}
	
	/**
	 * Ư�� ��ġ�� ������ �̵��Ѵ�.
	 * @param focus ȭ�� ����� �� ��ǥ
	 */
	public void focusOn(Vector2 focus) {
		startX = focus.x - width / 2;
		startY = focus.y - height / 2;
	}
}
