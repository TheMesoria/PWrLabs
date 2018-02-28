import org.omg.CORBA.DATA_CONVERSION;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			Questionnaire questionnaireCorrect = QuestionnaireLoader.Load("/home/black/Work/Labs/Lab1Task2/questionnaries/Correct.csv");
			Questionnaire questionnaireQ1 = QuestionnaireLoader.Load("/home/black/Work/Labs/Lab1Task2/questionnaries/Q1.csv");
			Questionnaire questionnaireQ2 = QuestionnaireLoader.Load("/home/black/Work/Labs/Lab1Task2/questionnaries/Q2.csv");
			Questionnaire questionnaireQ3 = QuestionnaireLoader.Load("/home/black/Work/Labs/Lab1Task2/questionnaries/Q3.csv");
			Questionnaire questionnaireQ4 = QuestionnaireLoader.Load("/home/black/Work/Labs/Lab1Task2/questionnaries/Q4.csv");

			questionnaireQ1.getNumberOfQuestions();
			QuestionnaireCollection questionnaireCollection = new QuestionnaireCollection(0.89f,new float[]{0.5f,0.7f},questionnaireCorrect);
			questionnaireCollection.addQuestionnaire(questionnaireQ1);
			questionnaireCollection.addQuestionnaire(questionnaireQ2);
			questionnaireCollection.addQuestionnaire(questionnaireQ3);
			questionnaireCollection.addQuestionnaire(questionnaireQ4);

			DataAnalyser.DrawHistogram(questionnaireCollection);
			System.out.println();
			DataAnalyser.DrawPassDiagram(questionnaireCollection);
		}
		catch (QuestionnaireMismatchException qme)
		{
			System.err.println("Questionairy failed to load!");
		}
		catch (Exception e)
		{
			System.err.println("Something went terribly wrong... "+e.getMessage());
		}
	}
}
