package catdog.mine;

import com.badlogic.gdx.Gdx;
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
	
	public static final int HEIGHT = 48;
	
	public InventoryView(Inventory model) {
		this.model = model;
		font = new BitmapFont();
		spriteBatch = new SpriteBatch();
		bgTex = new TextureRegion(new Texture(Gdx.files.internal("data/inventory_bg.png")), 0, 0, 512, 48);
	}

	public void render() {
		spriteBatch.begin();
		spriteBatch.draw(bgTex, 0, 0);
		for (int i = 0; i < Inventory.MAX_ITEMS; i++) {
			Item item = model.getItem(i);
			if (item != null) {
				spriteBatch.draw(item.getIconTex(), i * 48 + 8, 8);
				String count = Integer.toString(model.getItemCount(i));
				font.draw(spriteBatch, count, (i + 1) * 48 - 10 - font.getBounds(count).width, 30);
			}
		}
		spriteBatch.end();
	}
	
	public void onClick(Vector2 pos) {
		// ���ʿ������� i��° ĭ�� ������
		int i = 0;// (int)((480 - pos.y) / ENTRY_HEIGHT);
		if (i < Inventory.MAX_ITEMS) {
			// �������� ������
			selectedItem = model.getItem(i);
		} else {
			// ��ư�� ������
			selectedItem = null;
		}
	}
}
