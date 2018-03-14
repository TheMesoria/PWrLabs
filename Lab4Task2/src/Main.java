public class Main
{
	public static void main(String[] args)
	{
		ClassLoader cl = ClassLoader.getPlatformClassLoader();
		try
		{
			cl.loadClass("TspBrute.class");

		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
