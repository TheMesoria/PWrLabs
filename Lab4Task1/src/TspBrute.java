import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TspBrute
{
	private int[][] map;
	private int result = 0x99999;

	/**
	 *  Ehhh It works!~~ Leave it.
	 * @param path path to loaded file
	 */
	private void initMap(String path)
	{
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(path));
			int level=0;
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] cities = line.split(cvsSplitBy);
				if(map==null)
					map=new int[cities.length][cities.length];
				for(int i=0;i<cities.length;i++)
					map[level][i]=Integer.parseInt(cities[i]);

				level++;
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void bruteIt()
	{
		ArrayList<Integer> elements = new ArrayList<>();
		for(int i=0;i<map.length;i++)
			elements.add(i);

		for(int i=0;i<elements.size();i++)
		{
			int tmp = elements.remove(i);
			routeIt(elements,tmp);
			elements.add(i,tmp);
		}

	}
	private void routeIt(ArrayList<Integer> elements, Integer active)
	{
		for(int i=0;i<elements.size();i++)
		{
			elements.add(i,active);
			int length=getLength(elements);
			if(length<result) result=length;
			elements.remove(i);
		}
	}
	private int getLength(List<Integer> list)
	{
		int tmp=0;
		for(int i=1;i<list.size();i++)
			tmp += map[list.get(i - 1)][list.get(i)];
		return tmp;
	}
	public TspBrute(String path)
	{
		this.initMap(path);
		this.bruteIt();
	}

	public int getResult()
	{
		return result;
	}
}
