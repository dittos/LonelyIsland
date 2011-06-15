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
		treetexture = TextureDict.load("data/texture/tree/tree.png");
	}
	
	/**
	 * 생성자
	 * @param pos 나무 위치 (맵 좌표)
	 */
	public Tree(Vector2 pos) {
		this.pos = pos;
	}
	
	/**
	 * 나무가 살아있는지
	 * @return 살아있으면 true
	 */
	public boolean isAlive() {
		return timeleft > 0;
	}
	
	/**
	 * 나무 캐기
	 * @param delta : 나무를 캐는 시간
	 */
	public void chopped(float delta) {
		// 나무가 아직 살아있다면 남은 시간을 빼주기
		if(isAlive())
			timeleft -= delta;
	}
	
	public void render(SpriteBatch sprbatch, Viewport viewport) {
		// 실제 좌표로 변환하여 계산
		Vector2 screenpos = viewport.toScreen(pos);
		// 나무 텍스쳐를 고려하여 좌표 변경
		screenpos.x += 16 - treetexture.getWidth() / 2;
		// 파괴 정도에 따라 알파값 감소하여 그리기
		float alpha = (timeleft / destroyTime) / 2f + 0.5f;
		Color oldcolor = sprbatch.getColor();
		sprbatch.setColor(1,1,1,alpha);
		sprbatch.draw(treetexture, screenpos.x, screenpos.y);
		sprbatch.setColor(oldcolor);
	}
}
