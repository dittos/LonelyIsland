package catdog.mine;

public class Item implements Comparable<Item>
{
	private int itemID;
	private ItemProperty prop;
	
	public int getItemID()
	{
		return itemID;
	}

	public void setItemID(int itemID)
	{
		this.itemID = itemID;
		prop = ItemDB.getItemProperty(itemID);
	}

	public ItemProperty getProp()
	{
		return prop;
	}

	public Item(int itemID)
	{
		this.itemID = itemID;
		prop = ItemDB.getItemProperty(itemID);
	}
	
	public Item(Item item)
	{
		itemID = item.itemID;
		prop = item.prop;
	}

	@Override
	public int compareTo(Item target)
	{
		return itemID - target.itemID;
	}
	
	@Override
	public boolean equals(Object target)
	{
		return (target instanceof Item && ((Item)target).itemID == itemID);
	}
}
