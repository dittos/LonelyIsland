package catdog.mine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class CraftDialog implements InventoryView.OnItemSelected {
	private Inventory inventory;
	private Texture bgTex;
	private SpriteBatch spriteBatch; 
	
	private Item[] selectedItems;
	private Item combinedItem;
	
	private int x, y;
	private int[] xoffsets = {57, 177, 297, 417};
	
	public boolean shown = false;
	
	public static final int WIDTH = 512;
	public static final int HEIGHT = 128;
	
	private static final int ENTRY_WIDTH = 32;
	private static final int ENTRY_HEIGHT = 32;
	
	public CraftDialog(Inventory inventory) {
		this.inventory = inventory;
		bgTex = new Texture("data/craft_bg.png");
		spriteBatch = new SpriteBatch();
		selectedItems = new Item[3];
		x = (533 - bgTex.getWidth()) / 2;
		y = (320 - bgTex.getHeight()) / 2;
	}
	
	public boolean isInDialog(Vector2 pos)
	{
		if(!shown)
			return false;
		return (pos.x >= x && pos.x <= x + WIDTH && pos.y >= y && pos.y <= y + HEIGHT);
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
		combineItem();
	}
	
	public void onClick(Vector2 pos)
	{
		if(pos.y < y + 47 || pos.y > y + 47 + ENTRY_HEIGHT)
			return;
		
		int i;
		for(i = 0; i < 4; ++ i)
		{
			if(pos.x >= x + xoffsets[i] && pos.x <= x + xoffsets[i] + ENTRY_WIDTH)
				break;
		}
		
		if(i < 3)
			selectedItems[i] = null;
		else if(combinedItem != null)
		{
			for(i = 0; i < 3; ++ i)
			{
				if(selectedItems[i] != null)
				{
					inventory.removeItem(inventory.findItem(selectedItems[i]));
					if(inventory.findItem(selectedItems[i]) == -1)
						selectedItems[i] = null;
				}
			}
			
			inventory.addItem(combinedItem);
		}
	}
}
