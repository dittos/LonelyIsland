package catdog.mine;

import java.util.ArrayList;

public class Inventory
{
	/**
	 * 인벤토리에 들어있는 아이템 목록. 비어있는 칸은 <code>null</code>.
	 */
	private ArrayList<Item> itemList;
	
	/**
	 * 인벤토리에 들어있는 아이템의 수
	 */
	private int items;
	
	/**
	 * 인벤토리의 횡폭
	 */
	public static final int INVENTORY_WIDTH = 5;
	/**
	 * 인벤토리의 종폭
	 */
	public static final int INVENTORY_HEIGHT = 3;
	/**
	 * 인벤토리의 실제 크기
	 */
	public static final int MAX_ITEMS = INVENTORY_WIDTH * INVENTORY_HEIGHT;
	
	public Inventory()
	{
		itemList = new ArrayList<Item>(MAX_ITEMS);
		items = 0;
	}
	
	/**
	 * 인벤토리 비어있는 칸에 아이템을 추가한다.
	 * @param item 아이템 객체
	 * @return 성공 여부
	 */
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
	
	/**
	 * 해당하는 인벤토리 칸의 아이템을 제거한다.
	 * @param index 인벤토리 번호
	 * @return 성공 여부
	 */
	public boolean removeItem(int index)
	{
		if(itemList.get(index) != null)
		{
			itemList.set(index, null);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 해당하는 인벤토리 칸의 아이템 객체를 가져온다.
	 * @param index 인벤토리 번호.
	 * @return 해당하는 아이템 객체. 해당 칸이 비어있다면 <code>null</code>.
	 */
	public Item getItem(int index)
	{
		return itemList.get(index);
	}
	
	/**
	 * 인벤토리에 들어있는 아이템 수를 얻는다.
	 * @return 아이템 수
	 */
	public int getAvailableItems()
	{
		return items;
	}
}
