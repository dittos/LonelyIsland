package catdog.mine;

import java.util.ArrayList;

import catdog.mine.monster.Destructor;
import catdog.mine.monster.Mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	
	private Viewport viewport;
	private World world;
	private Player player;
	private ArrayList<Mob> monsters;
	private InventoryView inventoryView;
	private CraftDialog craftDialog;
	private boolean lastTickWasNight = false;

	@Override
	public void render(float delta) {
		boolean isNight = Clock.isNight();
		if (lastTickWasNight != isNight) {
			if (isNight) {
				// 방금 밤이 되었음
				// 몹이 젠!!
				//Mob mob = new Mob(world, player);
				Mob mob = new Destructor(world, player);
				mob.position.set(1, 50);
				monsters.add(mob);
			} else {
				// 방금 아침이 되었음
				// 몹이 펑!!
				monsters.clear();
			}
		}
		lastTickWasNight = isNight;
		
		if (Gdx.input.isTouched()) {
			// 터치 이벤트는 화면 왼쪽 위가 (0, 0)
			Vector2 touchPos = new Vector2(Gdx.input.getX(), viewport.screenHeight - Gdx.input.getY());
			
			if (touchPos.y <= InventoryView.HEIGHT && Gdx.input.justTouched()) {
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
							player.canPutBlock((int)mapPos.x, (int)mapPos.y) &&
							world.canPutBlock((int)mapPos.x, (int)mapPos.y)) {
						world.putBlock((int)mapPos.x, (int)mapPos.y, new Block(selectedItem));
						player.inventory.removeItem(player.inventory.findItem(selectedItem));
					}
				} else {
					// 이동 모드
					Block block = world.getBlock(mapPos);
					if (block != null) {
						if (player.isNear(mapPos))
							player.dig(block, delta);
					} else {
						// 나무가 있는지 확인
						Block baseblock = world.getBlock((int)mapPos.x, (int)mapPos.y - 1);
						if(baseblock != null && baseblock.isAlive()
								&& baseblock.getAttachedTree()!= null
								&& baseblock.getAttachedTree().isAlive()
								&& player.isNear(mapPos)) {
							// TODO : 나무를 캐는 코드
							player.chopTree(baseblock.getAttachedTree(), delta);
						} else
							if (Gdx.input.justTouched())
								player.requestMove(mapPos);
					}
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
		if (craftDialog.shown)
			craftDialog.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		world = new World();
		player = new Player(world);
		player.position.set(2, World.GROUND_ALTITUDE);
		monsters = new ArrayList<Mob>();
		inventoryView = new InventoryView(player.inventory);
		craftDialog = new CraftDialog(player.inventory);
		inventoryView.addOnItemSelectedHandler(craftDialog);
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
