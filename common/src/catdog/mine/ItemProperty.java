package catdog.mine;

import com.badlogic.gdx.graphics.Texture;

public class ItemProperty
{
	private String name;
	private String icon;
	private Texture iconTex;
	
	public Texture getIconTex() {
		return iconTex;
	}

	public void setIconTex(Texture iconTex) {
		this.iconTex = iconTex;
	}

	public String getName()
	{
		return name;
	}

	public String getIcon()
	{
		return icon;
	}
	
	public ItemProperty(String name, String icon)
	{
		this.name = name;
		this.icon = icon;
	}
}
