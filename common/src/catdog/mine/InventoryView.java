package catdog.mine;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InventoryView {
	private Inventory model;
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	
	private float x = 800 - WIDTH;
	private static float WIDTH = 48;
	
	public InventoryView(Inventory model) {
		this.model = model;
		font = new BitmapFont();
		spriteBatch = new SpriteBatch();
	}

	public void render() {
		spriteBatch.begin();
		for (int i = 0; i < Inventory.MAX_ITEMS; i++) {
			Item item = model.getItem(i);
			if (item != null)
				font.draw(spriteBatch, item.getProp().getName() + " (" + model.getItemCount(i) + ")", x, (Inventory.MAX_ITEMS - i) * 48);
		}
		spriteBatch.end();
	}
}
