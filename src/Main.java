import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main (String[] args){
        Scanner keyboard = new Scanner(System.in);
        boolean isDone=false;
        StringBuffer quizList = new StringBuffer();
        ArrayList<Quiz> quizzes= new ArrayList<>();
        String outOfRangeMsg = "Invalid Input: Option selected does not exist.";

        System.out.println("   ____        _           ");
        System.out.println("  / __ \\__  __(_)___  _____");
        System.out.println(" / / / / / / / /_  / / ___/");
        System.out.println("/ /_/ / /_/ / / / /_/ /    ");
        System.out.println("\\___\\_\\__,_/_/ /___/_/     ");

        System.out.println("Welcome to Quizr.\n");

        while(!isDone){
            int makeOrTake = takeNumericInput("What would you like to do?\n1) Make a Quiz\n2) Edit a Quiz\n3) Take a Quiz", 3, outOfRangeMsg);
            StringBuffer tmp;
            switch(makeOrTake){
                case 1: quizzes.add(new Quiz());
                        quizList=null; quizList=getQuizList(quizzes);
                        break;
                case 2:
                    if(quizzes.size()==0){ System.out.println("No quizzes to edit"); }
                    else { quizzes.get(takeNumericInput("Please choose a quiz to edit:\n"+quizList.toString(),quizzes.size(),outOfRangeMsg)).editQuiz(); }
                    break;
                case 3:
                    takeAQuiz(quizzes.get(takeNumericInput("Please choose a quiz to take:\n"+quizList.toString(),quizzes.size(),outOfRangeMsg)));
                    break;
                default:
                    break;
            }

            System.out.println("Are you done with Quizr? Yes/No");
            String yesOrNo = keyboard.next();
            if (yesOrNo.equals("Yes")){isDone=true;}
        }

        System.out.println("Thank you for using Quizr!!!");
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

    public static int[] takeNumericArrayInput(String prompt, int maxValue, String errorMsg) {
        Scanner keyboard = new Scanner(System.in);
        boolean isValid=false;
        String userInput;
        String[] answerList;
        int[] answerArray=new int[2];

        while(!isValid){
            System.out.println(prompt);
            userInput=keyboard.next();
            answerList=userInput.split(",");
            answerArray=null; answerArray=new int[answerList.length];
            boolean isGood=true;

            for (int i = 0; i < answerList.length; i++) {
                int tmp;
                try {
                    tmp=Integer.parseInt(answerList[i]);
                } catch(NumberFormatException e) {
                    System.out.println("Error: Input contains non-numeric value. Only numeric values and commas are valid for this input. Please try again.\n");
                    isValid=false; isGood=false;
                    break;
                }
                if ((tmp > 0) && (tmp <= maxValue)){answerArray[i]=tmp;}
                else {System.out.println(errorMsg); isValid=false; isGood=false; break;}
            }
            if (isGood){isValid=true;}
        }
        return answerArray;
    }

    private static void takeAQuiz(Quiz quiz){
        Scanner keyboard = new Scanner(System.in);
        Question[] questions=quiz.getQuestions();
        int userChoice;
        int score=0;
        String start;
        String choiceDoesNotExistMsg = "Invalid Input: That is not a valid answer choice";
        String correctAnswerMsg = "That's Correct!!!";
        String incorrectAnswerMsg = "Sorry, that's not correct.";

        System.out.println("Quiz Name: "+quiz.getName()+"\nQuiz Description: "+quiz.getQuestions()+"");
        System.out.println("Press any key to start the quiz:");
        start=keyboard.next();

        for (int i = 0; i < questions.length; i++) {
            if (questions[i] instanceof MultiAnswerQuest) {
                takeNumericArrayInput(questions[i].toString(),questions[i].getAnswerChoices().length,choiceDoesNotExistMsg);
            }
            else {
                userChoice=takeNumericInput(questions[i].toString(),questions[i].getAnswerChoices().length,choiceDoesNotExistMsg);
                if (userChoice==questions[i].correctAnswer){ System.out.println(correctAnswerMsg); score++; }
                else { System.out.println(incorrectAnswerMsg); }
            }


        }


    }

    private static StringBuffer getQuizList(ArrayList<Quiz> quizzes) {
        StringBuffer output=new StringBuffer();
        quizzes.forEach(quiz -> output.append(quizzes.indexOf(quiz)+". "+quiz.getName()+"\n"));
        return output;
    }
}