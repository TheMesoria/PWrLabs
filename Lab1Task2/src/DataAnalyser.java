import sun.awt.SunHints;

import java.util.HashMap;
import java.util.Map;

public class DataAnalyser
{
	public static int GetPercentage(Questionnaire correctAnswersSheet,  Questionnaire questionnaire) throws QuestionnaireMismatchException
	{
		float correctAnswers = questionnaire.compare(correctAnswersSheet);
		return (int)(100*correctAnswers/(float)correctAnswersSheet.getNumberOfQuestions());
	}
	public static void DrawHistogram(QuestionnaireCollection qc) throws QuestionnaireMismatchException
	{
		HashMap<Integer,Integer> state = new HashMap<>();
		for(Questionnaire questionnaire:qc.getQuestionnairesCollection())
		{
			int percentage = GetPercentage(qc.getCorrectAnswers(),questionnaire);
			if(state.putIfAbsent(percentage,1)!=null)
			{
				Integer tmp = state.get(percentage)+1;
				state.put(percentage,tmp);
			}
		}
		System.out.println("Histogram:");
		for(Map.Entry<Integer,Integer> entry:state.entrySet())
		{
			Integer percentage = entry.getKey();
			Integer value = entry.getValue();
			System.out.format("%3d%% | ",percentage);
			for(int i=0;i< value;i++)
				System.out.print("#");
			System.out.println();
		}
	}
	public static void DrawPassDiagram(QuestionnaireCollection qc) throws QuestionnaireMismatchException
	{
		int pass=0,failed=0;
		for(Questionnaire questionnaire:qc.getQuestionnairesCollection())
		{
			float result = GetPercentage(qc.getCorrectAnswers(),questionnaire);
			result/=100;
			if(result>qc.getFailGrade())
				pass++;
			else
				failed++;
		}
		System.out.println("Pass diagram:");
		System.out.print("Pass | ");
		for(int i=0;i<pass;i++)
			System.out.print("#");
		System.out.print("\nFail | ");
		for(int i=0;i<failed;i++)
			System.out.print("#");
		System.out.println();
	}
}
