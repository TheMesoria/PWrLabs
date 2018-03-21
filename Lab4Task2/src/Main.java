import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main
{
	private URLClassLoader classLoader;
	private HashMap<String,Class<?>> loadedClassHashMap =new HashMap<>();

	void setClassLoaders()
	{
		File file = new File("/home/black/Work/Labs/Lab4Task2/src");
		try
		{
			classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
			//loadedClassHashMap.putIfAbsent("TspBrute", classLoader.loadClass("TspBrute"));
			//loadedClassHashMap.putIfAbsent("TspRandom",classLoader.loadClass("TspRandom"));
			//loadedClassHashMap.putIfAbsent("TspNeighbour",classLoader.loadClass("TspNeighbour"));
		}
		catch (Exception e)
		{e.printStackTrace();}
	}
	private void present()
	{
		boolean working=true;
		try
		{
			Scanner scanner = new Scanner(System.in);
			ArrayList<Tsp> tspArr=new ArrayList<>();
			while (working)
			{
				String string = scanner.nextLine();
				if(string.compareTo("stop")==0){working=false;continue; }
				if(string.compareTo("size")==0){System.out.println(tspArr.size());}
				if(string.compareTo("unload")==0){tspArr=new ArrayList<>();continue;}
				if(string.compareTo("best")==0){Tsp[] ret=tspArr.toArray(new Tsp[0]);System.out.println("Best of: " + bestOf(ret));continue;}

				Tsp tsp = loadTspClass(string);
				if(tsp!=null)
				{
					tspArr.add(tsp);
				}
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
	private int bestOf(Tsp[] tsps)
	{
		try
		{
			Class<?> loadedClass = classLoader.loadClass("BestOf");
			Object o=loadedClass
					.getDeclaredConstructor()
					.newInstance();

			Method m = loadedClass.getMethod("bestOf",Tsp[].class);
			return (int)m.invoke(o,new Object[]{tsps});
		}
		catch (Exception e){return 0;}
	}
	private Tsp loadTspClass(String name)
	{
		Tsp tsp;
		try
		{
			Class<?> loadedClass = classLoader.loadClass(name);
			tsp=(Tsp)loadedClass
					.getDeclaredConstructor(String.class)
					.newInstance("/home/black/test.map");
		}catch(Exception e)
		{
			return null;
		}
		System.out.println("Loaded "+name);
		System.out.println("Description "+tsp.getDescription());
		System.out.println("Result "+tsp.getResult());
		return tsp;
	}
	public static void main(String[] args)
	{
		Main main = new Main();
		main.setClassLoaders();
		main.present();
	}
}
