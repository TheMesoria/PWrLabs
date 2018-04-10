package mo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="User")
public class User
{
	int id;
	String username,salt,password;
	public User()
	{
	}

	@XmlElement
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	@XmlElement
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	@XmlElement
	public String getSalt()
	{
		return salt;
	}
	public void setSalt(String salt)
	{
		this.salt = salt;
	}
	@XmlElement
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
