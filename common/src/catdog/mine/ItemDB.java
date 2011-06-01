package catdog.mine;

import java.util.*;

public class ItemDB
{
	/**
	 * 아이템 사전
	 */
	private static TreeMap<Integer, ItemProperty> itemDic;
	
	static
	{
		itemDic = new TreeMap<Integer, ItemProperty>();
		
		// 아이템 ID와 구체적인 아이템 속성을 연결
		itemDic.put(1, new ItemProperty("Item 1", "data/item1.png"));
	}
	
	/**
	 * <code>itemID</code>에 해당하는 아이템 속성을 얻어옴
	 * @param itemID 아이템 ID
	 * @return 아이템의 속성
	 */
	public static ItemProperty getItemProperty(int itemID)
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
		Set<Map.Entry<Integer, ItemProperty>> items = itemDic.entrySet();
		Iterator<Map.Entry<Integer, ItemProperty>> it = items.iterator();
		
		while(it.hasNext())
		{
			Map.Entry<Integer, ItemProperty> e = it.next();
			if(e.getValue().getName().equals(itemName))
				return e.getKey();
		}
		
		return -1;
	}
}
