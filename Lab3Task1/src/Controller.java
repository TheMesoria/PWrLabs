import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;

public class Controller
{
	private HashMap<Integer,SoftReference<LinkedList<Integer>>> dataHashMap = new HashMap<>();

	public void start() throws
						InterruptedException
	{
		CalculationRunner c1 = new CalculationRunner(this);
		c1.start();
		CalculationRunner c2 = new CalculationRunner(this);
		c2.start();
		CalculationRunner c3 = new CalculationRunner(this);
		c3.start();
		CalculationRunner c4 = new CalculationRunner(this);
		c4.start();
		CalculationRunner c5 = new CalculationRunner(this);
		c5.start();
	}

	synchronized public SoftReference<LinkedList<Integer>> getStructure(int seed)
	{
		if(!dataHashMap.containsKey(seed) || dataHashMap.get(seed)==null)
			dataHashMap.put(seed, new SoftReference<>(generateStructure(seed)));

		return dataHashMap.get(seed);
	}
	private LinkedList<Integer> generateStructure(int seed)
	{
		System.out.println("Allocating");
		LinkedList<Integer> generatedList = new LinkedList<Integer>();
		for(int i=1; i<10000000;i++)
		{
			generatedList.add(seed%i);
		}
		return generatedList;
	}
}
