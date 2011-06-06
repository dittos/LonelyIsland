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
		// 실제 좌표로 변환하여 계산
		Vector2 screenpos = viewport.toScreen(pos);
		// 나무 텍스쳐를 고려하여 좌표 변경
		screenpos.x += 16 - treetexture.getWidth() / 2;
		
		sprbatch.draw(treetexture, screenpos.x, screenpos.y);
	}
}
