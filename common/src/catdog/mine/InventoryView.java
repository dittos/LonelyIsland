package catdog.mine;

import java.util.ArrayList;

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
	private Item selectedItem = null;
	private ArrayList<OnItemSelected> onItemSelectedHandlers = new ArrayList<OnItemSelected>();
	
	public static final int HEIGHT = 46;
	private static final int ENTRY_WIDTH = 32 + 8;
	
	public interface OnItemSelected {
		public void onItemSelected(Item selectedItem);
	}
	
	public InventoryView(Inventory model) {
		this.model = model;
		font = new BitmapFont();
		spriteBatch = new SpriteBatch();
		bgTex = new TextureRegion(TextureDict.load("data/inventory_bg.png"), 0, 0, 512, HEIGHT);
	}

	public void render() {
		spriteBatch.begin();
		spriteBatch.draw(bgTex, 0, 0);
		for (int i = 0; i < Inventory.MAX_ITEMS; i++) {
			Item item = model.getItem(i);
			if (item != null) {
				spriteBatch.draw(item.getIconTex(), i * ENTRY_WIDTH + 8, 6);
				String count = Integer.toString(model.getItemCount(i));
				if (item == selectedItem)
					font.setColor(Color.RED);
				else
					font.setColor(Color.WHITE);
				font.draw(spriteBatch, count, (i + 1) * ENTRY_WIDTH - 3 - font.getBounds(count).width, 28);
			}
		}
		spriteBatch.end();
	}
	
	public void addOnItemSelectedHandler(OnItemSelected handler) {
		onItemSelectedHandlers.add(handler);
	}
	
	public void onClick(Vector2 pos) {
		// 위쪽에서부터 i번째 칸이 눌렸음
		int i = (int)(pos.x / ENTRY_WIDTH);
		if (i < Inventory.MAX_ITEMS) {
			// 아이템을 눌렀음
			selectedItem = model.getItem(i);
		} else {
			// 버튼을 눌렀음
			selectedItem = null;
		}
		
		for (OnItemSelected handler : onItemSelectedHandlers)
			handler.onItemSelected(getSelectedItem());
	}
	
	public Item getSelectedItem() {
		if (model.findItem(selectedItem) < 0)
			selectedItem = null;
		
		return selectedItem;
	}
}
