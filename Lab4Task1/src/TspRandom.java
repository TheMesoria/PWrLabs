import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TspRandom
{
	private long amountOfEntries;
	private int[][] map;
	private int result = 0x99999;

	public long getAmountOfEntries()
	{
		return amountOfEntries;
	}

	public String getDescription()
	{

		return description;
	}

	private final String description="Traveling sales man problem RANDOM";


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
	private void getRandomResult()
	{
		ArrayList<Integer> array = new ArrayList<>();
		ArrayList<Integer> order = new ArrayList<>();
		for(int i=0;i<map.length;i++)
			array.add(i);

		Random random = new Random();
		while(!array.isEmpty())
		{
			int randomNumber;
			if(array.size()==1) randomNumber=0; else
			randomNumber =  random.nextInt(array.size()-1);
			order.add(array.get(randomNumber));
			array.remove(randomNumber);
		}

		int result=0;
		for(int i=1;i<order.size();i++)
		{
			result+=map[order.get(i-1)][order.get(i)];
		}
		result+=map[order.get(order.size()-1)][order.get(0)];

		if(result<this.result) this.result=result;
	}
	public TspRandom (String path)
	{
		amountOfEntries = 1000;
		initMap(path);
		for(int i=0;i<this.amountOfEntries;i++) getRandomResult();
	}
	public int getResult()
	{
		return this.result;
	}
}
