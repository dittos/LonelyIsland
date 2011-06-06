package catdog.mine;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	
	private Viewport viewport;
	private World world;
	private Life player;
	private ArrayList<Mob> monsters;
	private InventoryView inventoryView;

	@Override
	public void render(float delta) {
		if (Gdx.input.isTouched()) {
			// 터치 이벤트는 화면 왼쪽 위가 (0, 0)
			Vector2 touchPos = new Vector2(Gdx.input.getX(), viewport.screenHeight - Gdx.input.getY());
			
			if (touchPos.y <= InventoryView.HEIGHT) {
				// 인벤토리를 터치했을 경우
				inventoryView.onClick(touchPos);
			} else {
				// 맵을 터치했을 경우
				Vector2 mapPos = viewport.fromScreen(touchPos);
				
				// 터치한 곳에 따라 캐릭터 시선 방향을 정한다.
				if (mapPos.x < player.position.x)
					player.setDirection(Life.LEFT);
				else
					player.setDirection(Life.RIGHT);
				
				Item selectedItem = inventoryView.getSelectedItem();
				if (selectedItem != null) {
					// 아이템 놓는 모드
					if (Gdx.input.justTouched() && player.isNear(mapPos) &&
							world.canPutBlock((int)mapPos.x, (int)mapPos.y)) {
						// TODO: 플레이어와 겹치는 부분에 아이템 못 놓도록
						world.putBlock((int)mapPos.x, (int)mapPos.y, new Block(selectedItem));
						player.inventory.removeItem(player.inventory.findItem(selectedItem));
					}
				} else {
					// 이동 모드
					Block block = world.getBlock(mapPos);
					if (block != null) {
						if (player.isNear(mapPos))
							player.dig(block, delta);
						//else
							// TODO: 손이 안 닿아도 뭔가 파는 시늉을?
					}
					else
						if (Gdx.input.justTouched())
							player.requestMove(mapPos);
				}
			}
		}
		
		player.update(delta);
		for (Mob mob : monsters)
			mob.update(delta);
		viewport.focusOn(player.position);
		
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		world.render(viewport);
		player.render(viewport);
		for (Mob mob : monsters)
			mob.render(viewport);
		inventoryView.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// 데이터 로딩
		Life.loadData();
		
		world = new World();
		monsters = new ArrayList<Mob>();
		Mob mob = new Mob(world);
		mob.position.set(5, 50);
		monsters.add(mob);
		player = new Life(world);
		player.position.set(2, World.GROUND_ALTITUDE);
		inventoryView = new InventoryView(player.inventory);
		viewport = new Viewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 32, 32);
		viewport.focusOn(player.position);
		ItemDB.loadTextures();
		Clock.reset();
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
