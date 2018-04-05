package crossroad;

import lights.Lights;

public interface LightSimulator
{
	/**
	 * @brief
	 * Adds road to crossroad
	 * @description
	 * Adds road to the current crossroad. It is possible to create infinite
	 * amount of roads connected in one place.
	 */
	void addRoad(Lights lights);

}
