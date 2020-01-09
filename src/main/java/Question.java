import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.NumberFormatException;

public class Question {

    private String questionText;
    private String[] answerChoices;
    private int correctAnswer;
    private final String outOfRangeMsg = "Invalid Input: Option selected does not exist.";

    public Question(String questionText, String[] answerChoices, int correctAnswer){
        setQuestionText(questionText);
        setAnswerChoices(answerChoices);
        setCorrectAnswer(correctAnswer);
    }

    public Question(){}

    public String getQuestionText() { return this.questionText; }

    public String[] getAnswerChoices() { return this.answerChoices; }

    public int getCorrectAnswer() { return correctAnswer; }

    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public void setAnswerChoices(String[] answerChoices) { this.answerChoices = answerChoices; }

    public void setCorrectAnswer(int correctAnswer) { this.correctAnswer = correctAnswer; }

    public void edit() {
        Scanner keyboard=new Scanner(System.in);
        boolean imDone=false;
        String isDone="No";
        int num;

        while (!imDone) {
            num=takeNumericInput("\nWhat would you like to edit?\n1) Text\n2) Answer Choices\n3) Change Correct Answer/s\n4) Go Back\n",4,this.outOfRangeMsg);
            switch(num){
                case 1:
                    System.out.println("\nText: "+this.questionText);
                    this.questionText=null;
                    System.out.print("Please enter the new text for this question: ");
                    this.questionText=keyboard.nextLine();
                    System.out.println("\nText: "+this.questionText);
                    break;

                case 2:
                    int choice=takeNumericInput("\nPlease choose the answer choice to edit:\n"+makeChoicesText(),this.answerChoices.length,this.outOfRangeMsg);
                    this.answerChoices[choice-1]=null;
                    System.out.print("Please enter the new value for choice #"+choice+": ");
                    answerChoices[choice-1]=keyboard.nextLine();
                    System.out.println("\nChoice #"+choice+": "+this.answerChoices[choice-1]);
                    break;

                case 3:
                    System.out.println("\nCorrect Answer: "+this.correctAnswer+") "+answerChoices[correctAnswer-1]);
                    this.correctAnswer=takeNumericInput("Please enter the new correct answer for this question: ",this.answerChoices.length,this.outOfRangeMsg);
                    System.out.println("The new correct answer is: "+this.correctAnswer+") "+answerChoices[correctAnswer-1]);
                    break;

                case 4: imDone=true; break;
            }

            /*while (!isDone.equals("Yes")){
                System.out.println("Are you done editing this question? Yes/No");
                isDone=keyboard.next();
                if (isDone.equals("Yes")){imDone=true;}
                else if (isDone.equals("No")){break;}
            }*/
        }
    }

    private int takeNumericInput(String prompt, int maxValue, String errorMsg) {
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

    private StringBuffer makeChoicesText() {
        StringBuffer text=new StringBuffer();
        for (int i = 0; i < answerChoices.length; i++) { text.append(i + 1).append(") ").append(answerChoices[i]).append("\n"); }
        return text;
    }

    @Override
    public String toString() { return this.questionText+"\n"+makeChoicesText()+"\n"; }
}



