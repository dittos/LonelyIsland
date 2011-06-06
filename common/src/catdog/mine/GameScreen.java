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
					player.setDirection(Player.LEFT);
				else
					player.setDirection(Player.RIGHT);
				
				if (inventoryView.selectedItem != null) {
					// 아이템 놓는 모드
					if (Gdx.input.justTouched() && player.isNear(mapPos) &&
							world.canPutBlock((int)mapPos.x, (int)mapPos.y)) {
						// TODO:
						// - 플레이어와 겹치는 부분에 아이템 못 놓도록
						// - 아이템이 0개가 되면 selectedItem 해제
						// - 블럭 객체를 현재 선택 아이템에 맞게 생성
						world.putBlock((int)mapPos.x, (int)mapPos.y, new Block(ItemDB.getItem(1)));
						player.inventory.removeItem(player.inventory.findItem(inventoryView.selectedItem));
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
		// 데이터 로딩
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
