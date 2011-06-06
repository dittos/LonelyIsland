package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class InventoryView {
	private Inventory model;
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private TextureRegion bgTex;
	public Item selectedItem = null;
	
	private final float x = 800 - WIDTH;
	public static final int WIDTH = 46;
	public static final int ENTRY_HEIGHT = 48;
	
	public InventoryView(Inventory model) {
		this.model = model;
		font = new BitmapFont();
		spriteBatch = new SpriteBatch();
		bgTex = new TextureRegion(new Texture(Gdx.files.internal("data/inventory_bg.png")), 0, 0, WIDTH, 480);
	}

	public void render() {
		spriteBatch.begin();
		spriteBatch.draw(bgTex, x, 0);
		for (int i = 0; i < Inventory.MAX_ITEMS; i++) {
			Item item = model.getItem(i);
			if (item != null) {
				int y = (Inventory.MAX_ITEMS - i + 1) * ENTRY_HEIGHT + (ENTRY_HEIGHT - 32)/2;
				spriteBatch.draw(item.getIconTex(), x + (WIDTH-32)/2, y);
				String count = Integer.toString(model.getItemCount(i));
				font.draw(spriteBatch, count, x + WIDTH - 10 - font.getBounds(count).width, y+22);
			}
		}
		spriteBatch.end();
	}
	
	public void onClick(Vector2 pos) {
		// 위쪽에서부터 i번째 칸이 눌렸음
		int i = (int)((480 - pos.y) / ENTRY_HEIGHT);
		if (i < Inventory.MAX_ITEMS) {
			// 아이템을 눌렀음
			selectedItem = model.getItem(i);
		} else {
			// 버튼을 눌렀음
			selectedItem = null;
		}
	}
}
