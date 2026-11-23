/*

Brett Core
Program 9
11/5/2026
CMPS 1617

This program emulates a calculator with all standard functions and a few advanced functions.
*/

import java.lang.Math;
import java.util.Scanner;
public class Program_9 {
    public static void main(String args[]){
        //Variables
        boolean run = true;
        Scanner input = new Scanner(System.in);
        String parserOutput;
        menuStartup();

        //Main Loop
        while(run){

            System.out.println("Input your calculations: ");
            String userInput = input.nextLine();

            parserOutput = Parser(userInput);

            if (userInput.equals("EXIT")){
                run = false;
                System.out.println("Thanks for using my calculator!");
            }

        }


        input.close();


    }

    public enum Operations{
        ADDITION("+", false, (num1, num2) -> num1 + num2),
        SUBTRACTION("-", false, (num1, num2) -> num1 - num2),
        MULTIPLICATION("*", false, (num1, num2) -> num1 * num2),
        DIVISION("/", false, (num1, num2) -> num1 / num2),
        EXPONENTIATION("^", false, (num1, num2) -> Math.pow(num1,num2)),
        MODULUS("%", false, (num1, num2) -> num1 % num2),
        SQUAREROOT("sqrt", true, (num1, num2) -> Math.sqrt(num1)),
        SIN("sin", true, (num1, num2) -> Math.sin(num1)),
        COS("cos", true, (num1, num2) -> Math.cos(num1)),
        TAN("tan", true, (num1, num2) -> Math.tan(num1)),
        MAX("max", true, (num1, num2) -> Math.max(num1, num2)),
        MIN("min", true, (num1, num2) -> Math.min(num1, num2)),
        AVERAGE("avg", true, (num1, num2) -> (num1 + num2) / 2),
        HYPOTENUSE("hyp", true, (num1, num2) -> Math.hypot(num1, num2)),
        EXIT("exit", false, null);

    //Enum Variables
    private String description;
    private boolean isFunction;
    private final Calculator operation;

    //Constructor
    private Operations(String description, boolean isFunction, Calculator operation){
        this.description = description;
        this.isFunction = isFunction;
        this.operation = operation;
    }

    //Getters
    public String getDescription(){
        return this.description;
    }
    public boolean getFunction(){
        return this.isFunction;
    }
    public Calculator operate(){
        return operation;
    }

    }
    @FunctionalInterface
    interface Calculator {
        double operation(double num1, double num2);
    }

    static public String Parser(String userInput){

        String temp;
        String[] parserOutput;

        
        
        return userInput;

    }

    static public void menuStartup(){

        System.out.println("=== Operations Menu ===");
        
        for (Operations operation : Operations.values()){

            System.out.print(operation.name() + ": " + operation.getDescription() + "\n");

        }



    }
}
