package mo;

public class ComboPc
{
	private PC pc_;
	private PcVendor pcVendor_;

	public ComboPc(PC pc_, PcVendor pcVendor_)
	{
		this.pc_ = pc_;
		this.pcVendor_ = pcVendor_;
	}

	public int getId()
	{
		return pc_.getId();
	}
	public String getName()
	{
		return pc_.getName();
	}
	public String getRam()
	{
		return pc_.getRam();
	}
	public String getVendorName()
	{
		return pcVendor_.getName();
	}
	public Integer getNip()
	{
		return pcVendor_.getNip();
	}
}
