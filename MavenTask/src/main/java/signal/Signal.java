package signal;

public interface Signal
{
	Object getPositiveSignal();
	Object getSteadySignal();
	Object getNegativeSignal();
	Object getOtherSignal(String type);
}
