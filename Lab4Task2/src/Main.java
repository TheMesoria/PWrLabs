import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class Main
{
	URLClassLoader classLoader;
	ArrayList<Class<?>> loadedClassArrayList=new ArrayList<>();

	void setClassLoaders()
	{
		File file = new File("/home/black/Work/Labs/Lab4Task2/src");
		try
		{
			classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
			loadedClassArrayList.add(classLoader.loadClass("TspBrute"));
			loadedClassArrayList.add(classLoader.loadClass("TspRandom"));
		}
		catch (Exception e)
		{e.printStackTrace();}
	}
	public void present()
	{
		try
		{
			for (Class<?> val : loadedClassArrayList)
			{
				Method mNam = val.getMethod("getDescription");
				Method mRes = val.getMethod("getResult");

				Object o=val
						.getDeclaredConstructor(String.class)
						.newInstance("/home/black/test.map");

				System.out.println("Info: "+mNam.invoke(o));
				System.out.println("Length: " + mRes.invoke(o));
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
	public static void main(String[] args)
	{
		Main main = new Main();
		main.setClassLoaders();
		main.present();
		if(true)return;
		File file = new File("/home/black/Work/Labs/Lab4Task2/src");
		try
		{
			URL url = file.toURI().toURL();

			URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
			Class<?> heheClass =  urlClassLoader.loadClass("TspBrute");

			Method heheMethod = heheClass.getMethod("getResult");
			heheMethod.setAccessible(true);
			int i = (int)heheMethod.invoke(
					heheClass
							.getDeclaredConstructor(String.class)
							.newInstance("/home/black/test.map")
										  );
			System.out.println(i);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
 		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
	}
}
