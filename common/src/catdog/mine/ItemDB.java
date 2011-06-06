package catdog.mine;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ItemDB
{
	/**
	 * 아이템 사전
	 */
	private static TreeMap<Integer, Item> itemDic;
	
	static
	{
		itemDic = new TreeMap<Integer, Item>();
		
		// 아이템 ID와 구체적인 아이템 속성을 연결
		itemDic.put(1, new Item(1, "Dirt", "data/block.png"));
	}
	
	/**
	 * <code>itemID</code>에 해당하는 아이템 속성을 얻어옴
	 * @param itemID 아이템 ID
	 * @return 아이템의 속성. 일치하는 아이템이 없다면 <code>null</code>.
	 */
	public static Item getItem(int itemID)
	{
		return itemDic.get(itemID);
	}
	
	/**
	 * 이름이 <code>itemName</code>인 아이템의 ID를 얻어옴
	 * @param itemName 아이템 이름
	 * @return 아이템 ID. 해당 이름의 아이템이 없다면 -1
	 */
	public static int getItemID(String itemName)
	{
		Set<Map.Entry<Integer, Item>> items = itemDic.entrySet();
		Iterator<Map.Entry<Integer, Item>> it = items.iterator();
		
		while(it.hasNext())
		{
			Map.Entry<Integer, Item> e = it.next();
			if(e.getValue().getName().equals(itemName))
				return e.getKey();
		}
		
		return -1;
	}
	
	/**
	 * 아이템 아이콘 텍스쳐를 불러온다.
	 */
	public static void loadTextures() {
		for (Item p : itemDic.values()) {
			p.setIconTex(new Texture(Gdx.files.internal(p.getIcon())));
		}
	}
}
