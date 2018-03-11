import java.util.LinkedList;
import java.util.Random;

public class CalculationRunner extends Thread
{
	Controller controller;
	CalculationRunner(Controller controller)
	{
		this.controller=controller;
	}
	private float calculateAverage()
	{
		Random random = new Random();
		int sum = 0;

		LinkedList<Integer> list = controller.getStructure(random.nextInt(10)).get();
		if(list==null)return -10;
		//System.out.println(list.toString());
		for (Integer aList : list)
			sum += aList;

		return sum/list.size();
	}

	@Override
	public void start()
	{
		while(true)
		{
			System.out.println(calculateAverage());
		}
	}
}
