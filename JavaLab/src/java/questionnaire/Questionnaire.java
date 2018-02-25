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
public void setAnswer(int index, Character answer)
{
    answerContainer.get(index).add(answer);
}
public int compare(Questionnaire questionnaire)
{
    int correctAnswerCounter = 0;
    //Initial Check
    if (questionnaire.numberOfQuestions != this.numberOfQuestions)
        return -1;
    if (questionnaire.amountOfPossibleAnswers != this.amountOfPossibleAnswers)
        return -1;

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
