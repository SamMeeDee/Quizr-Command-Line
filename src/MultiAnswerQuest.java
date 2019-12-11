import java.util.Scanner;

public class MultiAnswerQuest extends Question {
    int[] correctAnswers=null;
    String maxChoicesMsg;

    public MultiAnswerQuest() {
        setQuestionText();
        setNumChoices(10);
        setAnswerChoices();
        setCorrectAnswers();
    }

    public void setCorrectAnswers() {
        Scanner keyboard = new Scanner(System.in);
        maxChoicesMsg="Invalid Input: The maximum number of correct answers allowed is "+answerChoices.length/2+'.';
        correctAnswers=new int[takeNumericInput("How many correct answers would you like this question to have (Max allowed is"+answerChoices.length/2+")?",answerChoices.length/2,maxChoicesMsg)];
        for (int i = 0; i < correctAnswers.length; i++) {
            correctAnswers[i]=takeNumericInput("Please enter the value for answer #"+(i+1)+": ",answerChoices.length,outOfRangeMsg);
        }
        System.out.println("Answer Choices:\n"+getChoicesTxt());
    }
}
