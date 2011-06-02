package catdog.mine;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InventoryView {
	private Inventory model;
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	
	private float x = 600;
	private float y = 350;
	
	public InventoryView(Inventory model) {
		this.model = model;
		font = new BitmapFont();
		spriteBatch = new SpriteBatch();
	}

	public void render() {
		spriteBatch.begin();
		int index = 0;
		for (int row = 0; row < Inventory.INVENTORY_HEIGHT; row++)
			for (int col = 0; col < Inventory.INVENTORY_WIDTH; col++) {
				Item item = model.getItem(index);
				if (item != null)
					font.draw(spriteBatch, item.getProp().getName() + " (" + model.getItemCount(index) + ")", x, y);
				index++;
			}
		spriteBatch.end();
	}
}
