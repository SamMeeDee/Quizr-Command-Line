import org.w3c.dom.ls.LSOutput;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.NumberFormatException;

public class Quiz {
    private StringBuffer name=null;
    private StringBuffer description=null;
    private Question[] questions;
    private String outOfRangeMsg = "Invalid Input: Number of questions must be between 1 and 10.";
    private String invalidQuestNumMsg;

    public Quiz(){
        setName();
        setDescription();
        setQuestions();
        System.out.println("Quiz Summary:\n\n"+getQuizText());
    }

    public StringBuffer getName() {
        return name;
    }

    public StringBuffer getDescription() {
        return description;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public StringBuffer getQuizText() {
        StringBuffer text=new StringBuffer("Name: "+this.name+"\nDescription: "+this.description+"\n\nQuestions:\n");
        for (int i = 0; i < questions.length; i++) {
            text.append(i + 1).append(". ").append(questions[i].toString());
        }
        return text;
    }

    private Question getQuestion() {
        int num=0;
        num=takeNumericInput("Please enter question number: ",questions.length,invalidQuestNumMsg);
        return questions[num-1];
    }

    private void setName() {
        Scanner keyboard = new Scanner(System.in);

        if (this.name!=null){
            this.name=null;
            System.out.println("Please enter the new name for this quiz: ");
        } else {
            System.out.println("Please enter the name for this quiz: ");
        }
        this.name = new StringBuffer(keyboard.nextLine());
        System.out.println("Quiz Name: "+this.name);
    }

    private void setDescription() {
        Scanner keyboard = new Scanner(System.in);

        if (this.description!=null){
            this.description=null;
            System.out.println("Please enter the new description for this quiz: ");
        } else {
            System.out.println("Please enter the description for this quiz: ");
        }
        this.description = new StringBuffer(keyboard.nextLine());
        System.out.println("Quiz Description: "+this.description);
    }

    private void setQuestions() {
        int num = takeNumericInput("Please enter the number of questions this quiz will have (minimum is 1): ", 10, outOfRangeMsg);
        questions = new Question[num];
        invalidQuestNumMsg="Invalid Input: There is no question "+num+'.';
        for (int i = 0; i < num; i++) {
            switch (takeNumericInput("Please choose type of question:\n1)Single Answer\n2)Multi Answer",2,outOfRangeMsg)){
                case 1: questions[i]=new SingleAnswerQuest();
                        break;
                case 2: questions[i]=new MultiAnswerQuest();
                        break;
                default: break;
            }
        }
    }

    public void editQuestion() {
        Question question = getQuestion();
        question.editQuestion();
    }

    public void editQuiz() {
        Scanner keyboard=new Scanner(System.in);
        boolean imDone=false;
        StringBuffer isDone=null;
        int num=0;

        while (!imDone) {
            num=takeNumericInput("What would you like to edit?\n1) Name\n2) Description\n3) Questions\nPress 0 to cancel.",3,this.outOfRangeMsg);
            switch(num){
                case 1: setName();
                    break;
                case 2: setDescription();
                    break;
                case 3: editQuestion();
                default: imDone=true; isDone=new StringBuffer("Yes"); break;
            }

            while ((!isDone.equals("Yes")) && (!isDone.equals("No"))){
                System.out.println("Are you done editing this question? Yes/No");
                isDone=new StringBuffer(keyboard.nextLine());
                if (isDone.equals("Yes")){imDone=true;}
                else if (isDone.equals("No")){break;}
                else continue;
            }
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
                System.out.println("Error: Non-numeric value entered. Only numeric values are valid for this field. Please try again.\n");
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
