import java.util.InputMismatchException;
import java.util.Scanner;

public class Quiz {
    private String name=null;
    private String description=null;
    private Question[] questions;


    public Quiz(String name, String description, Question[] questions){
        setName(name);
        setDescription(description);
        setQuestions(questions);
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public Question[] getQuestions() { return questions; }

    public void setDescription(String description) { this.description = description; }

    public void setName(String name) { this.name = name; }

    public void setQuestions(Question[] questions) { this.questions = questions; }

    public void edit() {
        Scanner keyboard=new Scanner(System.in);
        boolean imDone=false;
        int num;
        final String invalidChoiceMsg = "Invalid Input: Option selected does not exist.\n";

        while (!imDone) {

            num=takeNumericInput("\nWhat would you like to edit?\n1) Name\n2) Description\n3) Questions\n4) Go Back\n",4, invalidChoiceMsg);
            switch(num){
                case 1:
                    System.out.println("\nName: "+this.name);
                    this.name=null;
                    System.out.print("Please enter the new name for this quiz: ");
                    setName(keyboard.nextLine());
                    System.out.println("\nName: "+this.name);
                    break;

                case 2:
                    System.out.println("\nDescription: "+this.description);
                    this.description=null;
                    System.out.print("Please enter the new description for this quiz: ");
                    setDescription(keyboard.nextLine());
                    System.out.println("\nDescription: "+this.description);
                    break;

                case 3:
                    questions[takeNumericInput("\nPlease choose a question:\n"+makeQuestionsList(),questions.length, invalidChoiceMsg)-1].edit();
                    break;

                case 4: imDone=true; break;
            }

            /*while (!isDone.equals("Yes")){
                System.out.println("Are you done editing this quiz? Yes/No");
                isDone=keyboard.nextLine();
                if (isDone.equals("Yes")){imDone=true;}
                else if (isDone.equals("No")){break;}
            }*/

        }
    }

    private StringBuffer makeQuizText() {
        StringBuffer text=new StringBuffer("Name: "+this.name+"\nDescription: "+this.description+"\n\nQuestions:\n");
        for (int i = 0; i < questions.length; i++) { text.append(i + 1).append(". ").append(questions[i].toString()); }
        return text;
    }

    private StringBuffer makeQuestionsList() {
        StringBuffer text=new StringBuffer();
        for (int i = 0; i < questions.length; i++) { text.append(i + 1).append(". ").append(questions[i].getQuestionText()).append("\n"); }
        return text;
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
