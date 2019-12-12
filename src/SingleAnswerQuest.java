import java.util.Scanner;

public class SingleAnswerQuest extends Question {
    protected int correctAnswer;

    public SingleAnswerQuest(){
        setQuestionText();
        setNumChoices(5);
        setAnswerChoices();
        setCorrectAnswer();
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer() {
        System.out.println(this.toString());
        this.correctAnswer = takeNumericInput("Please enter the number of the correct answer for this question: ",answerChoices.length,outOfRangeMsg);
        System.out.println("The correct answer is "+answerChoices[correctAnswer-1]);
    }
}
