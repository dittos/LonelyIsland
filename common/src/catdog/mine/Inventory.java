package catdog.mine;

import java.util.ArrayList;

class ItemEntry
{
	/**
	 * 아이템 객체
	 */
	public Item item;
	
	/**
	 * 중첩된 수량
	 */
	public int count;
	
	public ItemEntry(Item item, int count)
	{
		this.item = item;
		this.count = count;
	}
}

public class Inventory
{
	/**
	 * 인벤토리에 들어있는 아이템 목록. 비어있는 칸은 <code>null</code>.
	 */
	private ArrayList<ItemEntry> itemList;
	
	/**
	 * 인벤토리에 들어있는 아이템의 수
	 */
	private int items;
	
	/**
	 * 인벤토리의 실제 크기
	 */
	public static final int MAX_ITEMS = 8;
	
	public Inventory()
	{
		itemList = new ArrayList<ItemEntry>(MAX_ITEMS);
		for (int i = 0; i < MAX_ITEMS; i++)
			itemList.add(null);
		items = 0;
	}
	
	/**
	 * 인벤토리에 아이템을 한 개 추가한다.
	 * @param item 아이템 객체
	 * @return 성공 여부
	 */
	public boolean addItem(Item item)
	{
		// 해당 아이템이 있는 지 검사
		int index = findItem(item);
		if(index < 0) // 아이템이 없다면
		{
			// 빈 자리에 추가
			for(int i = 0; i < MAX_ITEMS; ++ i)
			{
				if(itemList.get(i) == null)
				{
					itemList.set(i, new ItemEntry(item, 1));
					++ items;
					return true;
				}
			}
		}
		else // 아이템이 있다면
		{
			++ itemList.get(index).count; // 수량을 1만큼 증가
			return true;
		}
		
		return false;
	}
	
	/**
	 * 해당하는 인벤토리 칸의 아이템을 한 개 제거한다.
	 * @param index 인벤토리 칸 번호
	 * @return 성공 여부
	 */
	public boolean removeItem(int index)
	{
		ItemEntry entry = itemList.get(index);
		if(entry != null)
		{
			-- entry.count;
			if(entry.count <= 0)
			{
				itemList.set(index, null);
				-- items;
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * 지정된 아이템이 들어있는 인벤토리 칸 번호를 찾는다.
	 * @param item 찾을 아이템 객체
	 * @return 인벤토리 칸 번호. 없다면 <code>-1</code>.
	 */
	public int findItem(Item item)
	{
		for(int i = 0; i < MAX_ITEMS; ++ i)
		{
			if(itemList.get(i) != null && itemList.get(i).item.equals(item))
				return i;
		}
		
		return -1;
	}
	
	/**
	 * 해당하는 인벤토리 칸의 아이템 객체를 가져온다.
	 * @param index 인벤토리 칸 번호
	 * @return 해당하는 아이템 객체. 해당 칸이 비어있다면 <code>null</code>.
	 */
	public Item getItem(int index)
	{
		ItemEntry entry = itemList.get(index);
		if(entry != null)
			return entry.item;
		return null;
	}
	
	/**
	 * 해당하는 인벤토리 칸의 아이템 수를 가져온다.
	 * @param index 인벤토리 칸 번호
	 * @return 해당 칸의 아이템 수. 해당 칸이 비어있다면 <code>-1</code>.
	 */
	public int getItemCount(int index)
	{
		ItemEntry entry = itemList.get(index);
		if(entry != null)
			return entry.count;
		return -1;
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
