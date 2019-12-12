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
            StringBuffer tmp;
            switch(makeOrTake){
                case 1: quizzes.add(new Quiz());
                        break;
                case 2:
                    if(quizzes.size()==0){ System.out.println("No quizzes to edit"); }
                    else { quizzes.get(takeNumericInput("Please choose a quiz to edit:\n"+getQuizList(quizzes).toString(),quizzes.size(),outOfRangeMsg)-1).editQuiz(); }
                    break;
                case 3:
                    if(quizzes.size()==0){ System.out.println("No quizzes to edit"); }
                    else{ takeAQuiz(quizzes.get(takeNumericInput("Please choose a quiz to take:\n"+getQuizList(quizzes).toString(),quizzes.size(),outOfRangeMsg)-1)); }
                    break;
                case 4:
                    System.out.println("Thank you for using Quizr!!!");
                    return;
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
        List<Integer> userChoices;
        int score=0;
        boolean isDone=false;
        String choiceDoesNotExistMsg = "Invalid Input: That is not a valid answer choice\n";
        String correctAnswerMsg = "That's Correct!!!\n";
        String incorrectAnswerMsg = "Sorry, that's not correct.\n";

        while (!isDone) {
            System.out.println("Quiz Name: "+quiz.getName()+"\nQuiz Description: "+quiz.getDescription()+"\n");

            for (Question question : questions) {
                if (question instanceof MultiAnswerQuest) {
                    MultiAnswerQuest maq = (MultiAnswerQuest) question;
                    userChoices = Arrays.stream(takeNumericArrayInput(maq.toString(), maq.getAnswerChoices().length, choiceDoesNotExistMsg))
                            .boxed()
                            .collect(Collectors.toList());
                    if (userChoices.size() != maq.getCorrectAnswers().size()) {
                        System.out.println("That's incorrect. You entered the wrong number of choices.\n");
                    } else if (userChoices.containsAll(maq.getCorrectAnswers())) {
                        System.out.println(correctAnswerMsg);
                        score++;
                    } else {
                        System.out.println("That's incorrect!! One or more of your choices was not a correct answer.\n");
                    }
                } else if (question instanceof SingleAnswerQuest) {
                    SingleAnswerQuest saq = (SingleAnswerQuest) question;
                    userChoice = takeNumericInput(saq.toString(), saq.getAnswerChoices().length, choiceDoesNotExistMsg);
                    if (userChoice == saq.correctAnswer) {
                        System.out.println(correctAnswerMsg);
                        score++;
                    } else {
                        System.out.println(incorrectAnswerMsg);
                    }
                }


            }

            System.out.println("Quiz Completed.\nYou got "+score+" out of "+questions.length+".\n");
            System.out.println("Would you like to try again? Yes/No");
            isDone=!"Yes".equals(keyboard.next());
        }
    }

    private static StringBuffer getQuizList(ArrayList<Quiz> quizzes) {
        StringBuffer output=new StringBuffer();
        quizzes.forEach(quiz -> output.append(quizzes.indexOf(quiz) + 1).append(". ").append(quiz.getName()).append("\n"));
        return output;
    }
}