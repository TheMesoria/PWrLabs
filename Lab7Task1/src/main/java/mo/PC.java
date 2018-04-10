package mo;

public class PC
{
	int id, vendorId;
	String name, ram;
	public PC(int id, int vendorId, String name, String ram)
	{
		this.id = id;
		this.vendorId = vendorId;
		this.name = name;
		this.ram = ram;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getVendorId()
	{
		return vendorId;
	}
	public void setVendorId(int vendorId)
	{
		this.vendorId = vendorId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getRam()
	{
		return ram;
	}
	public void setRam(String ram)
	{
		this.ram = ram;
	}
}
