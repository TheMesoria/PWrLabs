package signal;

public class RoadSignal implements Signal
{
	public enum Signal{GO,READY,STOP}

	public Signal getPositiveSignal()
	{
		return Signal.GO;
	}

	public Signal getSteadySignal()
	{
		return Signal.READY;
	}

	public Signal getNegativeSignal()
	{
		return Signal.STOP;
	}

	public Object getOtherSignal(String type)
	{
		return null;
	}
}
