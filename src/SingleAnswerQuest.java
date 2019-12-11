import java.util.Scanner;

public class SingleAnswerQuest extends Question {
    int correctAnswer;

    public SingleAnswerQuest(){
        setQuestionText();
        setNumChoices(5);
        setAnswerChoices();
        setCorrectAnswer();
    }
}
