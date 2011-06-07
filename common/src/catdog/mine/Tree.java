package catdog.mine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tree {
	private Vector2 pos;
	private static Texture treetexture;
	private static float destroyTime = 2.0f;
	private float timeleft = destroyTime;
	
	public static void LoadTexture() {
		treetexture = new Texture("data/texture/tree/tree.png");
	}
	
	/**
	 * ������
	 * @param pos ���� ��ġ (�� ��ǥ)
	 */
	public Tree(Vector2 pos) {
		this.pos = pos;
	}
	
	/**
	 * ������ ����ִ���
	 * @return ��������� true
	 */
	public boolean isAlive() {
		return timeleft > 0;
	}
	
	/**
	 * ���� ĳ��
	 * @param delta : ������ ĳ�� �ð�
	 */
	public void chopped(float delta) {
		// ������ ���� ����ִٸ� ���� �ð��� ���ֱ�
		if(isAlive())
			timeleft -= delta;
	}
	
	public void render(SpriteBatch sprbatch, Viewport viewport) {
		// ���� ��ǥ�� ��ȯ�Ͽ� ���
		Vector2 screenpos = viewport.toScreen(pos);
		// ���� �ؽ��ĸ� ����Ͽ� ��ǥ ����
		screenpos.x += 16 - treetexture.getWidth() / 2;
		
		// �ı� ������ ���� ���İ� �����Ͽ� �׸���
		float alpha = (timeleft / destroyTime) / 2f + 0.5f;
		Color oldcolor = sprbatch.getColor();
		sprbatch.setColor(1,1,1,alpha);
		sprbatch.draw(treetexture, screenpos.x, screenpos.y);
		sprbatch.setColor(oldcolor);
	}
}
