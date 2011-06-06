package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class InventoryView {
	private Inventory model;
	private BitmapFont font;
	private SpriteBatch spriteBatch;
	private TextureRegion bgTex;
	
	private float x = 800 - WIDTH;
	private static float WIDTH = 48;
	
	public InventoryView(Inventory model) {
		this.model = model;
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		spriteBatch = new SpriteBatch();
		bgTex = new TextureRegion(new Texture(Gdx.files.internal("data/inventory_bg.png")), 0, 0, WIDTH, 480);
	}

	public void render() {
		spriteBatch.begin();
		spriteBatch.draw(bgTex, x, 0);
		for (int i = 0; i < Inventory.MAX_ITEMS; i++) {
			Item item = model.getItem(i);
			if (item != null) {
				int y = (Inventory.MAX_ITEMS - i + 1) * 48;
				spriteBatch.draw(item.getProp().getIconTex(), x + (48-32)/2, y);
				font.draw(spriteBatch, item.getProp().getName() + " (" + model.getItemCount(i) + ")", x, y);
			}
		}
		spriteBatch.end();
	}
}
