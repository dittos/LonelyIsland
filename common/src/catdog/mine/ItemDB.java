package catdog.mine;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

class Combination implements Comparable<Combination>
{
	private int first, second, third;

	public int getFirst()
	{
		return first;
	}

	public int getSecond()
	{
		return second;
	}

	public int getThird()
	{
		return third;
	}
	
	public Combination(int first, int second, int third)
	{
		int temp;
		
		if(first > second)
		{
			temp = first;
			first = second;
			second = temp;
		}
		
		if(first > third)
		{
			temp = first;
			first = third;
			third = temp;
		}
		
		if(second > third)
		{
			temp = second;
			second = third;
			third = temp;
		}
		
		this.first = first;
		this.second = second;
		this.third = third;
	}

	@Override
	public int compareTo(Combination target)
	{
		if(first != target.first)
			return first - target.first;
		else if(second != target.second)
			return second - target.second;
		return third - target.third;
	}
	
	@Override
	public boolean equals(Object target)
	{
		if(target instanceof Combination)
		{
			Combination t = (Combination)target;
			return (first == t.first && second == t.second && third == t.third);
		}
		
		return false;
	}
}

public class ItemDB
{
	/**
	 * 아이템 사전
	 */
	private static TreeMap<Integer, Item> itemDic;
	
	/**
	 * 조합 사전
	 */
	private static TreeMap<Combination, Item> combDic;
	
	static
	{
		itemDic = new TreeMap<Integer, Item>();
		
		// 아이템 ID와 구체적인 아이템 속성을 연결
		itemDic.put(1, new Item(1, "Dirt", "data/block.png"));
		
		combDic = new TreeMap<Combination, Item>();
		
		// 조합할 아이템 ID와 조합된 아이템 ID를 연결 (조합할 아이템이 2개만 있으면 나머지 ID를 0으로 부여)
		combDic.put(new Combination(2, 3, 0), getItem(1)); // Item 2 + Item 3 = Item 1
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
		for(Map.Entry<Integer, Item> e: itemDic.entrySet())
		{
			if(e.getValue().getName().equals(itemName))
				return e.getKey();
		}
		
		return -1;
	}
	
	/**
	 * item1, item2, item3을 조합해서 나오는 아이템을 얻는다.
	 * @param item1 조합할 아이템 객체 1
	 * @param item2 조합 아이템 객체 2
	 * @param item3 조합 아이템 객체 3. 2개의 아이템 조합이라면 <code>null</code>.
	 * @return 조합된 아이템 객체. 조합이 없다면 <code>null</code>.
	 */
	public static Item getCombination(Item item1, Item item2, Item item3)
	{
		int first, second, third;
		if(item1 == null)
			first = 0;
		else
			first = item1.getItemID();
		
		if(item2 == null)
			second = 0;
		else
			second = item2.getItemID();
		
		if(item3 == null)
			third = 0;
		else
			third = item3.getItemID();
		
		return combDic.get(new Combination(first, second, third));
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
