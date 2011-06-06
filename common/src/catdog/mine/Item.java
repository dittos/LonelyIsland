package catdog.mine;

import com.badlogic.gdx.graphics.Texture;

public class Item implements Comparable<Item>
{
	private int itemID;
	private String name;
	private String icon;
	private Texture iconTex;
	
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

	public Item(int itemID, String name, String icon)
	{
		this.itemID = itemID;
		this.name = name;
		this.icon = icon;
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
