import java.util.LinkedList;

public class QuestionnaireCollection
{
	private float failGrade;
	private float[] grading;

	private Questionnaire correctAnswers;
	private LinkedList<Questionnaire> questionnairesCollection = new LinkedList<>();

	/**
	 * Default Constructor
	 * <p>
	 *     Creates the collection
	 * </p>
	 *
	 * @param failGrade - float, which defines what is the last fail grade
	 * @param grading - float, tells you what are different grades
	 * @param correctAnswers - correct Answers to the Test
	 */
	public QuestionnaireCollection(float failGrade, float[] grading, Questionnaire correctAnswers)
	{
		this.failGrade = failGrade;
		this.grading = grading;
		this.correctAnswers = correctAnswers;
	}

	public void addQuestionnaire(Questionnaire questionnaire)
	{
		try { this.correctAnswers.compare(questionnaire); }
		catch (QuestionnaireMismatchException qme) { return; }

		this.questionnairesCollection.add(questionnaire);
	}
	public float getFailGrade() { return this.failGrade; }
	public float[] getGrading() { return this.grading; }
	public Questionnaire getCorrectAnswers() { return this.correctAnswers; }
	public LinkedList<Questionnaire> getQuestionnairesCollection() { return this.questionnairesCollection; }
}