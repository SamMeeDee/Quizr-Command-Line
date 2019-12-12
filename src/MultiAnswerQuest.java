import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MultiAnswerQuest extends Question {
    protected List<Integer> correctAnswers=null;
    protected String maxChoicesMsg;

    public MultiAnswerQuest() {
        setQuestionText();
        setNumChoices(10);
        setAnswerChoices();
        maxChoicesMsg="Invalid Input: The maximum number of correct answers allowed is "+answerChoices.length/2+".\n";
        setCorrectAnswer();
    }

    public void setCorrectAnswer() {
        int[] userInput=new int[takeNumericInput("How many correct answers would you like this question to have (Max allowed is "+answerChoices.length/2+")?",answerChoices.length/2,maxChoicesMsg)];

        for (int i = 0; i < userInput.length; i++) {
            userInput[i]=takeNumericInput("Please enter the value for answer #"+(i+1)+": ",answerChoices.length,outOfRangeMsg);
        }
        correctAnswers= Arrays.stream(userInput).boxed().collect(Collectors.toList());
        System.out.println("The correct answers for this question are "+correctAnswers.toString()+".\n");
    }

    public List<Integer> getCorrectAnswers() {
        return correctAnswers;
    }
}
