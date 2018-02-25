package java.questionnaire;

import java.util.*;

public class Questionnaire
{
private int numberOfQuestions;
private int amountOfPossibleAnswers;
private LinkedList<LinkedList<Character>> answerContainer;
public Questionnaire(int numberOfQuestions, int amountOfPossibleAnswers)
{
    this.numberOfQuestions=numberOfQuestions;
    this.amountOfPossibleAnswers =amountOfPossibleAnswers;
    answerContainer=new LinkedList<>();
    for (int i=0;i<this.numberOfQuestions;i++)
        answerContainer.add(new LinkedList<>());
}
public void defineQuestionaire(LinkedList<Character>... Answers) throws QuestionnaireMismatchException
{
    if(Answers.length > this.numberOfQuestions)
        throw new QuestionnaireMismatchException();

    for(int i=0;i<Answers.length;i++)
    {
        if(Answers[i].size()!=this.amountOfPossibleAnswers)
            throw new QuestionnaireMismatchException();

        this.answerContainer.set(i,Answers[i]);
    }
}
public void setAnswer(int index, LinkedList<Character> answer) throws IndexOutOfBoundsException
{
    //Check if amount of answers is in range
    if(index < 0 || index > this.numberOfQuestions)
        throw new IndexOutOfBoundsException();
    this.answerContainer.set(index, answer);
}
public int compare(Questionnaire questionnaire) throws QuestionnaireMismatchException
{
    int correctAnswerCounter = 0;
    //Initial Check
    if (questionnaire.numberOfQuestions != this.numberOfQuestions)
        throw new QuestionnaireMismatchException();
    if (questionnaire.amountOfPossibleAnswers != this.amountOfPossibleAnswers)
        throw new QuestionnaireMismatchException();

    for (int i = 0; i < this.numberOfQuestions; i++) {
        boolean goodAnswer = true;
        if (questionnaire.answerContainer.get(i).size() != this.answerContainer.get(i).size())
            continue;
        for (int j = 0; j < questionnaire.answerContainer.get(i).size(); j++)
            if (questionnaire.answerContainer.get(i).get(j) != this.answerContainer.get(i).get(j))
                goodAnswer = false;
        if (goodAnswer) correctAnswerCounter++;
    }

    return correctAnswerCounter;
}
}
