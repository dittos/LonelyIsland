package catdog.mine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	
	private Viewport viewport;
	private World world;
	private Player player;
	private InventoryView inventoryView;

	@Override
	public void render(float delta) {
		if (Gdx.input.isTouched()) {
			// ��ġ �̺�Ʈ�� ȭ�� ���� ���� (0, 0)
			Vector2 touchPos = new Vector2(Gdx.input.getX(), viewport.screenHeight - Gdx.input.getY());
			
			if (touchPos.y <= InventoryView.HEIGHT) {
				// �κ��丮�� ��ġ���� ���
				inventoryView.onClick(touchPos);
			} else {
				// ���� ��ġ���� ���
				Vector2 mapPos = viewport.fromScreen(touchPos);
				
				// ��ġ�� ���� ���� ĳ���� �ü� ������ ���Ѵ�.
				if (mapPos.x < player.position.x)
					player.setDirection(Player.LEFT);
				else
					player.setDirection(Player.RIGHT);
				
				if (inventoryView.selectedItem != null) {
					// ������ ���� ���
					if (Gdx.input.justTouched() && player.isNear(mapPos) &&
							world.canPutBlock((int)mapPos.x, (int)mapPos.y)) {
						// TODO:
						// - �÷��̾�� ��ġ�� �κп� ������ �� ������
						// - �������� 0���� �Ǹ� selectedItem ����
						// - �� ��ü�� ���� ���� �����ۿ� �°� ����
						world.putBlock((int)mapPos.x, (int)mapPos.y, new Block(ItemDB.getItem(1)));
						player.inventory.removeItem(player.inventory.findItem(inventoryView.selectedItem));
					}
				} else {
					// �̵� ���
					Block block = world.getBlock(mapPos);
					if (block != null) {
						if (player.isNear(mapPos))
							player.dig(block, delta);
						//else
							// TODO: ���� �� ��Ƶ� ���� �Ĵ� �ô���?
					}
					else
						if (Gdx.input.justTouched())
							player.requestMove(mapPos);
				}
			}
		}
		
		player.update(delta);
		viewport.focusOn(player.position);
		
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		world.render(viewport);
		player.render(viewport);
		inventoryView.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// ������ �ε�
		Player.loadData();
		
		world = new World();
		player = new Player(world);
		player.position.set(2, World.GROUND_ALTITUDE);
		inventoryView = new InventoryView(player.inventory);
		viewport = new Viewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 32, 32);
		viewport.focusOn(player.position);
		ItemDB.loadTextures();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
