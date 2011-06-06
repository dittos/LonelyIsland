package catdog.mine;

import com.badlogic.gdx.graphics.Texture;

public class Item implements Comparable<Item>
{
	private Texture iconTex;
	
	// 아이템 프로퍼티
	
	private int itemID;
	private String name;
	private String icon;
	
	/**
	 * (블록 상태에서) 파괴에 걸리는 시간
	 */
	private float destroytime;
	
	public int getItemID()
	{
		return itemID;
	}

	public String getName()
	{
		return name;
	}

	public String getIcon()
	{
		return icon;
	}

	public Texture getIconTex()
	{
		return iconTex;
	}

	public void setIconTex(Texture iconTex)
	{
		this.iconTex = iconTex;
	}

	public float getDestroytime() {
		return destroytime;
	}
	
	public Item(int itemID, String name, String icon, float destroytime)
	{
		this.itemID = itemID;
		this.name = name;
		this.icon = icon;
		
		this.destroytime = destroytime;
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
