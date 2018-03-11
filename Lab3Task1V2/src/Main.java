public class Main
{
	public static void main(String[] args)
	{
		ThreadManager threadManager = new ThreadManager(5);
		threadManager.start();
	}
}
