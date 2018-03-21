public class BestOf
{
	public int bestOf(Tsp[] args)
	{
		int best=0x99999;
		for(Tsp tsp:args)
		{
			best=best>tsp.getResult()?tsp.getResult():best;
		}
		return best;
	}
}
