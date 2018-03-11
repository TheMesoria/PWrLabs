import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;

import static java.lang.Thread.*;

public class ThreadManager
{
	private LinkedList<CalculationRunner> calculationRunnerLinkedList = new LinkedList<>();
	private final HashMap<Long, SoftReference<StorageInstance>> storageInstancesHashMap = new HashMap<>();
	private ReferenceQueue<StorageInstance> storageInstanceSoftReferenceQueue = new ReferenceQueue<>();

	public LinkedList<Integer> getKey(long key)
	{
		synchronized (storageInstancesHashMap)
		{
			long amount=1000000;
			System.out.println("Requesting seed: "+key);
			storageInstancesHashMap.putIfAbsent(
					key,
					new SoftReference<StorageInstance>(new StorageInstance(amount,key),storageInstanceSoftReferenceQueue)
											   );
			if(storageInstancesHashMap.get(key).get()==null)
			{
				System.out.println("Found empty request...");
				storageInstancesHashMap.put(
						key,
						new SoftReference<StorageInstance>(new StorageInstance(amount,key),storageInstanceSoftReferenceQueue)
										   );
			}

			StorageInstance si = storageInstancesHashMap.get(key).get();
			return si==null?null:si.getElements();
		}
	}

	public void start()
	{
		for(CalculationRunner calculationRunner:calculationRunnerLinkedList)
		{
			System.out.println("Launching!");
			Thread thread = new Thread(calculationRunner);
			thread.start();
			System.out.println("READY.");
		}
		int amountOfDumps=0;
		while(true)
		{
			if(storageInstanceSoftReferenceQueue.poll() != null)
			{
				amountOfDumps++;
				System.err.println("Dump detected. Amount of dumps: "+amountOfDumps);
			}
		}
	}

	public ThreadManager(long amount)
	{
		for(long i=0;i<amount;i++)
			calculationRunnerLinkedList.add(new CalculationRunner(this));
	}

}
