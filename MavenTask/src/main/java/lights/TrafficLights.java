package lights;

import signal.RoadSignal;

public class TrafficLights implements Lights
{
	private RoadSignal.KnownSignal activeSignal;

	public TrafficLights()
	{
		activeSignal=RoadSignal.KnownSignal.STOP;
	}

	public void setActiveStatus(Object status)
	{
		activeSignal = (RoadSignal.KnownSignal) status;
	}

	public Object getActiveStatus()
	{
		return activeSignal;
	}

	public void switchLight()
	{
		activeSignal=
				activeSignal==RoadSignal.KnownSignal.GO?
				RoadSignal.KnownSignal.STOP:
				RoadSignal.KnownSignal.GO;
	}
}
