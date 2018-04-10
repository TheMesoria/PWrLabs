package mo;

public class PcVendor
{
	int id, nip;
	String name;
	public PcVendor(int id, int nip, String name)
	{
		this.id = id;
		this.nip = nip;
		this.name = name;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getNip()
	{
		return nip;
	}
	public void setNip(int nip)
	{
		this.nip = nip;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
