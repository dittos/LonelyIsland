package catdog.mine;

public class ItemProperty
{
	private String name;
	private String icon;
	
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
