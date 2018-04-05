package signal;

public class RoadSignal implements Signal
{
	public enum KnownSignal
	{GO,READY,STOP}

	public KnownSignal getPositiveSignal()
	{
		return KnownSignal.GO;
	}

	public KnownSignal getSteadySignal()
	{
		return KnownSignal.READY;
	}

	public KnownSignal getNegativeSignal()
	{
		return KnownSignal.STOP;
	}

	public Object getOtherSignal(String type)
	{
		return null;
	}
}
