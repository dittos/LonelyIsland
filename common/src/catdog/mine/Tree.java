package catdog.mine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Tree {
	private Vector2 pos;
	private static Texture treetexture;
	
	public static void LoadTexture() {
		treetexture = new Texture("data/texture/tree/tree.png");
	}
	
	public Tree(Vector2 pos) {
		this.pos = pos;
	}
	
	public void render(SpriteBatch sprbatch, Viewport viewport) {
		// ���� ��ǥ�� ��ȯ�Ͽ� ���
		Vector2 screenpos = viewport.toScreen(pos);
		// ���� �ؽ��ĸ� ����Ͽ� ��ǥ ����
		screenpos.x += 16 - treetexture.getWidth() / 2;
		
		sprbatch.draw(treetexture, screenpos.x, screenpos.y);
	}
}
