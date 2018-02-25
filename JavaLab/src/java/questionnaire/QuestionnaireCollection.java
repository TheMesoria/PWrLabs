package java.questionnaire;

import java.util.LinkedList;

public class QuestionnaireCollection
{
    float failGrade;
    float[] grading;

    Questionnaire correctAnswers;
    LinkedList<Questionnaire> questionnairesCollection;

    public QuestionnaireCollection(float failGrade, float[] grading, Questionnaire correctAnswers)
    {
        this.failGrade=failGrade;
        this.grading=grading;
        this.correctAnswers=correctAnswers;
    }
    public void addQuestionnaire(Questionnaire questionnaire)
    {
        
    }
}
