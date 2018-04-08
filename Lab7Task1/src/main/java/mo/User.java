package mo;

public class User
{
	int id;
	String username,salt,password;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getSalt()
	{
		return salt;
	}
	public void setSalt(String salt)
	{
		this.salt = salt;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public User(int id, String username, String salt, String password)
	{

		this.id = id;
		this.username = username;
		this.salt = salt;
		this.password = password;
	}
}
