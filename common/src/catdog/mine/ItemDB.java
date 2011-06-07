package catdog.mine;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import catdog.mine.specialblock.Electower;
import catdog.mine.specialblock.Firetower;
import catdog.mine.specialblock.Hotstone;
import catdog.mine.specialblock.Magnet;

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
		itemDic.put(1, new Item(1, "Dirt", "data/texture/block/dirt.png",
				null, 0,
				.3f,
				true, World.GROUND_ALTITUDE, 100, 10));
		itemDic.put(2, new Item(2, "Stone", "data/texture/block/stone.png",
				null, 0,
				0.8f,
				true, 0, 300, 14));
		itemDic.put(3, new Item(3, "Coal", "data/texture/block/coal.png",
				null, 0,
				0.6f,
				true, World.GROUND_ALTITUDE - 7, 20, 5));
		itemDic.put(4, new Item(4, "Iron", "data/texture/block/iron.png",
				null, 0,
				2f,
				true, World.GROUND_ALTITUDE - 14, 15, 3));
		itemDic.put(5, new Item(5, "Bronze", "data/texture/block/bronze.png",
				null, 0,
				1.4f,
				true, World.GROUND_ALTITUDE - 10, 18, 3));
		itemDic.put(6, new Item(6, "Gold", "data/texture/block/gold.png",
				null, 0,
				2.5f,
				true, World.GROUND_ALTITUDE - 19, 10, 2));
		itemDic.put(7, new Item(7, "Uranium", "data/texture/block/uranium.png",
				null, 0,
				4f,
				true, World.GROUND_ALTITUDE - 23, 8, 3));
		itemDic.put(8, new Item(8, "BedRock", "data/texture/block/bedrock.png",
				null, 0,
				444444444444f,
				false, 0, 0, 0));
		itemDic.put(9, new Item(9, "Wood", "data/texture/block/tree.png",
				null, 0,
				1f,
				false, 0, 0, 0));
		
		itemDic.put(10, new Item(10, "Magnet", "data/texture/block/magnet.png",
				new Magnet(), 5,
				1f,
				false, 0, 0, 0));
		itemDic.put(11, new Item(11, "Hot Stone", "data/texture/block/hotstone.png",		
				new Hotstone(),3,
				1f,
				false, 0, 0, 0));
		itemDic.put(12, new Item(12, "TNT", "data/texture/block/tnt.png",
				null, 0,
				1f,
				false, 0, 0, 0));
		itemDic.put(13, new Item(13, "Barricade", "data/texture/block/barricade.png",
				null, 0,
				1f,
				false, 0, 0, 0));
		itemDic.put(14, new Item(14, "Electric Tower", "data/texture/block/electower.png",
				new Electower(), 5,
				1f,
				false, 0, 0, 0));
		itemDic.put(15, new Item(15, "Fire Tower", "data/texture/block/firetower.png",
				new Firetower(), 0,
				1f,
				false, 0, 0, 0));
		itemDic.put(16, new Item(16, "Machine", "data/texture/block/machine.png",
				null, 0,
				1f,
				false, 0, 0, 0));
		
		combDic = new TreeMap<Combination, Item>();
		
		// 조합할 아이템 ID와 조합된 아이템 ID를 연결 (조합할 아이템이 2개만 있으면 나머지 ID를 0으로 부여)
		combDic.put(new Combination(4, 1, 2), getItem(10)); // Iron + Dirt + Stone = Magnet
		combDic.put(new Combination(2, 3, 0), getItem(11)); // Stone + Coal = Hot Stone
		combDic.put(new Combination(11, 4, 7), getItem(12)); // Hot Stone + Iron + Uranium = TNT
		combDic.put(new Combination(9, 1, 0), getItem(13)); // Wood + Dirt = Barricade
		combDic.put(new Combination(10, 16, 0), getItem(14)); // Magnet + Machine = Electric Tower
		combDic.put(new Combination(11, 16, 0), getItem(15)); // Hot Stone + Machine = Fire Tower
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
	
	public static Collection<Item> getAllItems() {
		return itemDic.values();
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
