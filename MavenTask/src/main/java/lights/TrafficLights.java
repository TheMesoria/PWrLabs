package lights;

import signal.Signal;

public class TrafficLights implements Lights
{
	private Signal activeSignal;

	public void setActiveStatus(Object status)
	{
		activeSignal = (Signal) status;
	}

	public Object getActiveStatus()
	{
		return activeSignal;
	}
}
