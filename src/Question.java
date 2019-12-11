import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.NumberFormatException;

public class Question {

    protected StringBuffer questionText;
    protected StringBuffer[] answerChoices;
    protected String outOfRangeMsg = "Invalid Input: Option selected does not exist.";
    protected int correctAnswer;

    public Question(){
        setQuestionText();
        setNumChoices(4);
        setAnswerChoices();
    }

    public StringBuffer getQuestionText() { return this.questionText; }

    public StringBuffer[] getAnswerChoices() { return this.answerChoices; }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    protected String getChoicesTxt () {
        StringBuffer choicesString=new StringBuffer();
        for (int i = 0; i < this.answerChoices.length-1; i++) {
            choicesString.append(this.answerChoices[i]).append("\n");
        }
        return choicesString.toString();
    }

    protected void setQuestionText() {
        Scanner keyboard=new Scanner(System.in);
        String input;

        if (this.questionText!=null){
            this.questionText=null;
            System.out.println("Please enter the new text for this question:");
        } else {
            System.out.println("Please enter the text for this question:");
        }
        this.questionText = new StringBuffer(keyboard.nextLine());
        System.out.println("The text for this question is \""+this.questionText+"\"");

    }

    protected void setAnswerChoice(){
        Scanner keyboard = new Scanner(System.in);
        int num=0;
        num=takeNumericInput("Which answer choice would you like to edit? ",this.answerChoices.length,this.outOfRangeMsg);
        System.out.println("The current value for choice #"+num+" is: "+this.answerChoices[num]);
        System.out.println("Please enter the new value for this choice:");
        this.answerChoices[num]=null; this.answerChoices[num]=new StringBuffer(num+") "+keyboard.nextLine());
        System.out.println("The new value for choice #"+ num +" is "+ this.answerChoices[num]);
    }

    protected void setNumChoices (int max) {
        String maxNumChoicesMsg = "Invalid: The maximum number of questions is "+max+'.';
        answerChoices = new StringBuffer[takeNumericInput("How many answer choices will this question have? ",max,maxNumChoicesMsg)];
    }

    protected void setAnswerChoices() {
        Scanner keyboard = new Scanner(System.in);

        for (int i = 0; i < answerChoices.length; i++) {
            System.out.println("Please enter the value for answer choice #"+(i+1)+": ");
            answerChoices[i]=new StringBuffer(i+") "+keyboard.nextLine());
        }

        System.out.println("Answer Choices:\n"+getChoicesTxt());
    }

    public void setCorrectAnswer() {
        System.out.println(this.toString());
        this.correctAnswer = takeNumericInput("Please enter the number of the correct answer for this question: ",answerChoices.length,outOfRangeMsg);
        System.out.println("The correct answer is "+answerChoices[correctAnswer]);
    }

    public void editQuestion() {
        Scanner keyboard=new Scanner(System.in);
        boolean imDone=false;
        String isDone=null;
        int num=0;

        while (!imDone) {
            num=takeNumericInput("What would you like to edit?\n1) Question Text\n2) Answer Choices",2,this.outOfRangeMsg);
            switch(num){
                case 1: setQuestionText();
                        break;
                case 2: setAnswerChoice();
                        break;
                default: break;
            }

            while ((!isDone.equals("Yes")) && (!isDone.equals("No"))){
                System.out.println("Are you done editing this question? Yes/No");
                isDone=keyboard.next();
                if (isDone.equals("Yes")){imDone=true;}
                else if (isDone.equals("No")){break;}
                else continue;
            }
        }
    }

    protected int takeNumericInput(String prompt, int maxValue, String errorMsg) {
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

    @Override
    public String toString() { return this.questionText+"\n"+getChoicesTxt(); }
}



