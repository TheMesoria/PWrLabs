import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TspNeighbour implements Tsp
{
	private long amountOfEntries;
	private int[][] map;
	private int result = 0x0;

	private final String description="Traveling sales man problem NEIGHBOUR";


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

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public int getResult()
	{
		return result;
	}

	private void calcMap()
	{
		ArrayList<Integer> elements = new ArrayList<>();
		for(int i=0;i<map.length;i++)
			elements.add(i);

		result+=map[elements.get(0)][elements.get(elements.size()-1)];

		int i=elements.size();
		Integer last=0;
		for(int j=0;j<i;j++)
		{
			routeIt(elements,last);
		}
	}

	private void routeIt(ArrayList<Integer> elements, Integer active)
	{
		int best = 0x9999999,bestI=-1;
		for(int i=0;i<elements.size();i++)
		{
			int tmp = map[active][elements.get(i)];
			if(tmp<best)
			{
				best=tmp;
				bestI=i;
			}
		}
		active=elements.get(bestI);
		elements.remove(bestI);
		result+=best;
	}

	public TspNeighbour(String path)
	{
		this.initMap(path);
		calcMap();
	}
}
