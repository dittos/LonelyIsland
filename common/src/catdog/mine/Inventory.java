package catdog.mine;

import java.util.ArrayList;

public class Inventory
{
	private ArrayList<Item> itemList;
	private int items;
	
	private static final int INVENTORY_WIDTH = 5;
	private static final int INVENTORY_HEIGHT = 3;
	
	private static final int MAX_ITEMS = INVENTORY_WIDTH * INVENTORY_HEIGHT;
	
	public Inventory()
	{
		itemList = new ArrayList<Item>(MAX_ITEMS);
		items = 0;
	}
	
	public boolean addItem(Item item)
	{
		for(int i = 0; i < MAX_ITEMS; ++ i)
		{
			if(itemList.get(i) == null)
			{
				itemList.set(i, item);
				++ items;
				return true;
			}
		}
		
		return false;
	}
	
	public boolean removeItem(int index)
	{
		if(itemList.get(index) != null)
		{
			itemList.set(index, null);
			return true;
		}
		
		return false;
	}
	
	public Item getItem(int index)
	{
		return itemList.get(index);
	}
	
	public int getAvailableItems()
	{
		return items;
	}
}
