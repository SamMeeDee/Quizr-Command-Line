import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.NumberFormatException;

public abstract class Question {

    protected StringBuffer questionText;
    protected StringBuffer[] answerChoices;
    protected String outOfRangeMsg = "Invalid Input: Option selected does not exist.";

    public Question(){

    }

    public StringBuffer getQuestionText() { return this.questionText; }

    public StringBuffer[] getAnswerChoices() { return this.answerChoices; }

    protected String getChoicesTxt () {
        StringBuffer choicesString=new StringBuffer();
        for (int i = 0; i < this.answerChoices.length; i++) {
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

    protected void setNumChoices (int max) {
        String maxNumChoicesMsg = "Invalid: The maximum number of questions is "+max+'.';
        answerChoices = new StringBuffer[takeNumericInput("How many answer choices will this question have? ",max,maxNumChoicesMsg)];
    }

    protected void setAnswerChoice(){
        Scanner keyboard = new Scanner(System.in);
        int num=takeNumericInput("Which answer choice would you like to edit? ",this.answerChoices.length,this.outOfRangeMsg);
        System.out.println("The current value for choice #"+num+" is: "+this.answerChoices[num-1]);
        System.out.println("Please enter the new value for this choice:");
        this.answerChoices[num-1]=null; this.answerChoices[num-1]=new StringBuffer(num+") "+keyboard.nextLine());
        System.out.println("The new value for choice #"+ num +" is "+ this.answerChoices[num-1]);
    }

    protected void setAnswerChoices() {
        Scanner keyboard = new Scanner(System.in);

        for (int i = 0; i < answerChoices.length; i++) {
            System.out.println("Please enter the value for answer choice #"+(i+1)+": ");
            answerChoices[i]=new StringBuffer((i+1)+") "+keyboard.nextLine());
        }

        System.out.println("Answer Choices:\n"+getChoicesTxt());
    }

    public void setCorrectAnswer() {}

    public void editQuestion() {
        Scanner keyboard=new Scanner(System.in);
        boolean imDone=false;
        String isDone="No";
        int num;

        while (!imDone) {
            num=takeNumericInput("What would you like to edit?\n1) Question Text\n2) Answer Choices\n3) Change Correct Answer/s\n4) Go Back",3,this.outOfRangeMsg);
            switch(num){
                case 1: setQuestionText(); break;
                case 2: setAnswerChoice(); break;
                case 3: setCorrectAnswer(); break;
                case 4: imDone=true; isDone="Yes"; break;
            }

            while (!isDone.equals("Yes")){
                System.out.println("Are you done editing this question? Yes/No");
                isDone=keyboard.next();
                if (isDone.equals("Yes")){imDone=true;}
                else if (isDone.equals("No")){break;}
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
    public String toString() { return this.questionText+"\n"+getChoicesTxt()+"\n"; }
}



