package database;

public class DataBaseController
{
	public static DataBaseController getInstance() {return instance_;}
	private static DataBaseController instance_;

	static { instance_ = new DataBaseController("jdbc:postgresql://localhost:5432/javalabtmp");}

	private boolean connectionStatus_;
	private String connectionString_;


	private DataBaseController(String connectionString)
	{
		connectionString_=connectionString;
	}
}
