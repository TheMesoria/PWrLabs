package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseController
{
	public static DataBaseController getInstance() {return instance_;}
	private static DataBaseController instance_;

	static { instance_ = new DataBaseController("jdbc:postgresql://localhost:5432/java");}

	private boolean isConnected_ = false;
	private String connectionString_;
	private Connection connection_;

	private DataBaseController(String connectionString) { connectionString_ = connectionString; }
	public boolean connect(String username, String password)
	{
		try
		{
			connection_ = DriverManager.getConnection(connectionString_, username, password);
			isConnected_ = true;
		} catch (SQLException e)
		{
			connection_ = null;
			return false;
		}
		return true;
	}
	public boolean isConnectionReady() {return isConnected_;}
	public Connection getConnection_()
	{
		return connection_;
	}
	public boolean tryToLogin(String username, String password)
	{
		String salt, hashedPassword;
		try
		{
			ResultSet rs = connection_
					.prepareStatement("SELECT t.*, CTID FROM lab.app_user t WHERE username = '" + username + "'")
					.executeQuery();
			if (!rs.next()) return false;

			salt = rs.getString("salt");
			hashedPassword = rs.getString("password");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5sum = md.digest((salt+password).getBytes());
			String output = String.format("%032X", new BigInteger(1, md5sum));

			System.err.println(output + " == " + hashedPassword);
			return output.equals(hashedPassword);

		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
