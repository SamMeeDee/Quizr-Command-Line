import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main (String[] args){
        Scanner keyboard = new Scanner(System.in);
        boolean isDone=false;
        ArrayList<Quiz> quizzes= new ArrayList<>();
        String outOfRangeMsg = "Invalid Input: Option selected does not exist.\n";

        System.out.println("   ____        _           ");
        System.out.println("  / __ \\__  __(_)___  _____");
        System.out.println(" / / / / / / / /_  / / ___/");
        System.out.println("/ /_/ / /_/ / / / /_/ /    ");
        System.out.println("\\___\\_\\__,_/_/ /___/_/     ");

        System.out.println("Welcome to Quizr.\n");

        while(!isDone){
            int makeOrTake = takeNumericInput("What would you like to do?\n1) Make a Quiz\n2) Edit a Quiz\n3) Take a Quiz\n4) Exit Quizr\n", 4, outOfRangeMsg);
            switch(makeOrTake){
                case 1: quizzes.add(makeAQuiz());
                        break;
                case 2:
                    if(quizzes.size()==0){ System.out.println("No quizzes to edit"); }
                    else { quizzes.get(takeNumericInput("\nPlease choose a quiz to edit:\n"+makeQuizList(quizzes).toString(),quizzes.size(),outOfRangeMsg)-1).edit(); }
                    break;
                case 3:
                    if(quizzes.size()==0){ System.out.println("No quizzes to edit"); }
                    else{ takeAQuiz(quizzes.get(takeNumericInput("\nPlease choose a quiz to take:\n"+makeQuizList(quizzes).toString(),quizzes.size(),outOfRangeMsg)-1)); }
                    break;
                case 4:
                    System.out.println("Thank you for using Quizr!!!");
                    return;
            }
        }

        System.out.println("Thank you for using Quizr!!!");
    }

    private static Quiz makeAQuiz(){
        Scanner keyboard = new Scanner(System.in);
        String name;
        String description;
        Question[] questions;
        String outOfRangeMsg = "Invalid Input: Option selected does not exist.\n";

        System.out.print("\nPlease enter the name for this quiz: ");
        name = keyboard.nextLine();
        System.out.println("Quiz Name: "+name);

        System.out.print("\nPlease enter the description for this quiz: ");
        description = keyboard.nextLine();
        System.out.println("Quiz Description: "+description);

        int num = takeNumericInput("\nPlease enter the number of questions this quiz will have (minimum is 1): ", 10, outOfRangeMsg);
        questions = new Question[num];
        for (int i = 0; i < questions.length; i++) { questions[i] = makeAQuestion(i+1); }

        return new Quiz(name,description,questions);
    }

    private static Question makeAQuestion(int num) {
        Scanner keyboard = new Scanner(System.in);
        String questionText;
        String[] answerChoices;
        int correctAnswer;
        final String maxNumChoicesMsg = "Invalid Input: Maximum number of answer choices is 5.";
        final String outOfRangeMsg = "Invalid Input: Option selected does not exist.\n";

        System.out.print("\nPlease enter the text for question "+num+": ");
        questionText = keyboard.nextLine();
        System.out.println("Question "+num+" Text: \""+questionText+"\"");

        answerChoices = new String[takeNumericInput("\nHow many answer choices will this question have? (Maximum is 5)",5,maxNumChoicesMsg)];
        for (int i = 0; i < answerChoices.length; i++) {
            System.out.print("\nPlease enter the value for answer choice #"+(i+1)+": ");
            answerChoices[i]=keyboard.nextLine();
        }
        System.out.println("\nThe answer choices for question "+num+" are: ");
        for (int i = 0; i < answerChoices.length; i++) { System.out.println((i+1)+") "+answerChoices[i]); }

        correctAnswer=takeNumericInput("\nPlease enter the number of the correct answer choice for question "+num+": ",answerChoices.length,outOfRangeMsg);
        System.out.println("Question "+num+" Correct Answer: "+correctAnswer+") "+answerChoices[correctAnswer-1]);

        return new Question(questionText,answerChoices,correctAnswer);
    }

    private static void takeAQuiz(Quiz quiz){
        Scanner keyboard = new Scanner(System.in);
        Question[] questions=quiz.getQuestions();
        int userChoice;
        int score=0;
        boolean isDone=false;
        String choiceDoesNotExistMsg = "Invalid Input: That is not a valid answer choice\n";
        String correctAnswerMsg = "That's Correct!!!\n";
        String incorrectAnswerMsg = "Sorry, that's not correct.\n";

        while (!isDone) {
            System.out.println("\nQuiz Name: "+quiz.getName()+"\nQuiz Description: "+quiz.getDescription()+"\n");

            for (Question question : questions) {
                userChoice = takeNumericInput(question.toString(), question.getAnswerChoices().length, choiceDoesNotExistMsg);
                if (userChoice == question.getCorrectAnswer()) {
                    System.out.println(correctAnswerMsg);
                    score++;
                } else {
                    System.out.println(incorrectAnswerMsg);
                }
            }

            System.out.println("Quiz Completed.\nYou got "+score+" out of "+questions.length+".\n");
            System.out.print("Would you like to try again (Yes/No)? ");
            isDone=!"Yes".equals(keyboard.next());
        }
    }

    private static StringBuffer makeQuizList(ArrayList<Quiz> quizzes) {
        StringBuffer output=new StringBuffer();
        quizzes.forEach(quiz -> output.append(quizzes.indexOf(quiz) + 1).append(". ").append(quiz.getName()).append("\n"));
        return output;
    }

    private static int takeNumericInput(String prompt, int maxValue, String errorMsg) {
        Scanner keyboard = new Scanner(System.in);
        boolean isValid=false;
        int userInput=0;

        while(!isValid){
            System.out.println(prompt);

            try {
                userInput=keyboard.nextInt();
            } catch(InputMismatchException e) {
                System.out.println("Error: Non-numeric value entered. Only numeric values are valid for this field.\n");
                keyboard.nextLine();
                isValid=false;
                continue;
            }

            if ((userInput > 0) && (userInput <= maxValue)) {isValid=true;}
            else {System.out.println(errorMsg); isValid=false;}
        }

        return userInput;
    }
}