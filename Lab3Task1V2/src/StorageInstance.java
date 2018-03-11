import jdk.jfr.Unsigned;

import java.util.LinkedList;
import java.util.Random;

public class StorageInstance
{
	private LinkedList<Integer> elements = new LinkedList<>();

	public LinkedList<Integer> getElements()
	{
		return new LinkedList<>(elements);
	}

	public StorageInstance(long amount, long seed)
	{
		Random random = new Random(seed);
		for(long i=0;i<=amount;i++)
			elements.add(random.nextInt(49)+1);

	}
}
