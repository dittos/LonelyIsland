package catdog.mine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CraftDialog implements InventoryView.OnItemSelected {
	private Inventory inventory;
	private Texture bgTex;
	private SpriteBatch spriteBatch; 
	
	private Item[] selectedItems;
	private Item combinedItem;
	
	private int x, y;
	private int[] xoffsets = {57, 177, 297, 417};
	
	public boolean shown = false;
	
	public CraftDialog(Inventory inventory) {
		this.inventory = inventory;
		bgTex = new Texture("data/craft_bg.png");
		spriteBatch = new SpriteBatch();
		selectedItems = new Item[3];
		x = (533 - bgTex.getWidth()) / 2;
		y = (320 - bgTex.getHeight()) / 2;
	}

	public void render() {
		spriteBatch.begin();
		spriteBatch.setColor(1, 1, 1, 0.9f);
		spriteBatch.draw(bgTex, x, y);
		for (int i = 0; i < selectedItems.length; i++) {
			if (selectedItems[i] != null)
				spriteBatch.draw(selectedItems[i].getIconTex(), x + xoffsets[i], y + 47);
		}
		
		if(combinedItem != null)
			spriteBatch.draw(combinedItem.getIconTex(), x + xoffsets[3], y + 47);
		spriteBatch.end();
	}
	
	public void combineItem()
	{
		combinedItem = ItemDB.getCombination(selectedItems[0], selectedItems[1], selectedItems[2]);
	}

	@Override
	public void onItemSelected(Item selectedItem) {
		for (int i = 0; i < selectedItems.length; i++) {
			if (selectedItems[i] == null) {
				selectedItems[i] = selectedItem;
				break;
			}
		}
	}
}
