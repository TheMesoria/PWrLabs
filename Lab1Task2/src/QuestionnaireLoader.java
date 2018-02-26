import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class QuestionnaireLoader
{
	static public Questionnaire Load(String filePath) throws QuestionnaireMismatchException
	{
		BufferedReader br = null;
		String cvsSplitBy = ",";
		String line = "";
		LinkedList<LinkedList<Character>> resultList =new LinkedList<>();
		try
		{
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null)
			{
				// use comma as separator
				String[] input = line.split(cvsSplitBy);
				LinkedList<Character> res = new LinkedList<>();
				for(String var :input)
				{
					char[] character = var.toCharArray();
					res.add(character[0]);
				}
				resultList.add(res);
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
				try{ br.close(); }
				catch (IOException e) { e.printStackTrace(); }
		}


		Questionnaire questionnaire = new Questionnaire(resultList.size()-1,resultList.pop().size());
		LinkedList<Character>[] tmp = new LinkedList[resultList.size()];
		for(int i=0;i<resultList.size();i++)
			tmp[i] = resultList.get(i);
		questionnaire.defineQuestionaire(tmp);
		System.out.println("Questionnaire added. Name: "+filePath);
		return questionnaire;
	}
}
