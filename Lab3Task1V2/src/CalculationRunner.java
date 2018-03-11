import jdk.jfr.Unsigned;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class CalculationRunner implements Runnable
{
	static long counter=0;
	private long id;
	private ThreadManager tm;
	private FileWriter tw;

	public CalculationRunner(ThreadManager tm)
	{
		id=++counter;
		try
		{
			tw=new FileWriter(Long.toString(id)+".log",true);
			tw.write("Initialised.");
			tw.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.tm = tm;
	}

	@Override
	public void run()
	{
		Random random = new Random();
		while(true)
		{
			float tmp=calculateAverage(random.nextInt(49)+1);
			System.out.println(tmp);
			try{tw.write("Average: "+Float.toString(tmp)+"\n");tw.flush();}catch (IOException e){e.printStackTrace();}
			if(tmp==0) // Unlikely to happen
				break;
			try{Thread.sleep(100);}
			catch (InterruptedException e){e.printStackTrace();} //Totally useless, yet we need catch
		}
	}

	private float calculateAverage(long seed)
	{
		Float sum=0f;
		LinkedList<Integer> clone = tm.getKey(seed);

		for(int val:clone)
			sum+=val;

		return sum/clone.size();
	}
}
