package catdog.mine;

import java.util.*;

public class ItemDB
{
	private static TreeMap<Integer, ItemProperty> itemDic;
	
	static
	{
		itemDic = new TreeMap<Integer, ItemProperty>();
		
		itemDic.put(1, new ItemProperty("Item 1", "data/item1.png"));
	}
	
	public static ItemProperty getItemProperty(int itemID)
	{
		return itemDic.get(itemID);
	}
	
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
