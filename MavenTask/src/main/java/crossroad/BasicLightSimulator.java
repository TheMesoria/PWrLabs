package crossroad;

import javafx.scene.effect.Light;
import lights.Lights;

import java.util.ArrayList;

public class BasicLightSimulator implements LightSimulator
{
	private ArrayList<Lights> lights_;

	public void addRoad(Lights lights)
	{
		lights_.add(lights);
	}

	public ArrayList<Lights> getLights_()
	{
		return lights_;
	}

	public void setLights_(ArrayList<Lights> lights_)
	{
		this.lights_ = lights_;
	}
}
