/*

Brett Core
Program 9
11/5/2026
CMPS 1617

This program emulates a calculator with all standard functions and a few advanced functions.

This program uses the Shunting Yard Algorithm (labeled SYA in the program), which turns math notation into Reverse Polish Notation, which is the notation used for determining orders of operations.


NOTE:  I did not come up with the algorithms for the SYA nor its evaluation function, I found and altered them with assistance of AI (sorry) linked here:

https://brilliant.org/wiki/shunting-yard-algorithm/

*/

import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;


public class Program_9 {
    public static void main(String args[]){
        //Variables
        boolean run = true;
        Scanner input = new Scanner(System.in);
        
        menuStartup();

        //Main Loop
        while(run){

            System.out.println("Input your calculations: ");
            String userInput = input.nextLine();


            if (userInput.equalsIgnoreCase("exit") ){
                run = false;
                System.out.println("Thanks for using my calculator!");
                input.close();
                return;
            }

            ArrayList<String> tokens = Tokenizer(userInput);
            //System.out.println("Tokens: " + tokens);

            ArrayList<String> SYA_result = SYA(tokens);
            //System.out.println("SYA: " + SYA_result);

            double result = Evaluate(SYA_result);
            System.out.println("Result: " + result);


        }


        input.close();


    }


    // Methods
    public enum Operations{
        //Binary Operators
        ADDITION("+", 1, 2, false, (num1, num2) -> num1 + num2),
        SUBTRACTION("-", 1, 2, false, (num1, num2) -> num1 - num2),
        MULTIPLICATION("*", 2, 2, false, (num1, num2) -> num1 * num2),
        DIVISION("/", 2, 2, false, (num1, num2) -> {
            
            if (num2 == 0){
                throw new ArithmeticException("Cannot divide by zero");
            }
            
            return num1 / num2;
        }),
        MODULUS("%", 2, 2, false, (num1, num2) -> {
            
            if (num2 == 0){
                throw new ArithmeticException("Cannot divide by zero");
            }
            
            return num1 % num2;
        }),
        EXPONENTIATION("^", 3, 2, false, (num1, num2) -> Math.pow(num1,num2)),
        
        //Functions
        SQUAREROOT("sqrt", 4, 1, true, (num1, num2) -> {
            
            if (num1 < 0){
                throw new ArithmeticException("Cannot square root a negative number");
            }

            return Math.sqrt(num1);
        }),
        SIN("sin", 4, 1, true, (num1, num2) -> Math.sin(num1)),
        COS("cos", 4, 1, true, (num1, num2) -> Math.cos(num1)),
        TAN("tan", 4, 1, true, (num1, num2) -> {
           
            if ((num1 % ((Math.PI) / 2.0)) == 0 && (num1 % Math.PI != 0)){
                throw new ArithmeticException("Incorrect domain for tangent function");
            }

            return Math.tan(num1);
        }),
        MAX("max", 4, 2, true, (num1, num2) -> Math.max(num1, num2)),
        MIN("min", 4, 2, true, (num1, num2) -> Math.min(num1, num2)),
        AVERAGE("avg", 4, 2, true, (num1, num2) -> (num1 + num2) / 2),
        HYPOTENUSE("hyp", 4, 2, true, (num1, num2) -> Math.hypot(num1, num2)),
        ABSOLUTE_VALUE("abs", 4, 1, true, (num1, num2) -> Math.abs(num1)),
        FLOOR("floor", 4, 1, true, (num1, num2) -> Math.floor(num1)),
        CEILING("ceil", 4, 1, true, (num1, num2) -> Math.ceil(num1)),
        ROUND("round", 4, 1, true, (num1, num2) -> Math.round(num1)),
        LOG_CUSTOM_BASE("logb", 4, 2, true, (num1, num2) -> {
            
            if (num1 <= 0){
                throw new ArithmeticException("Incorrect domain for logarithms (must be positive number)");
            }

            if (num2 <= 0 || num2 == 1){
                throw new ArithmeticException("Cannot have non-positive logarithm base or base of 1");
            }

            if (Math.log(num2) == 0){
                throw new ArithmeticException("Cannot divide by zero");
            }

            return Math.log(num1) / Math.log(num2);

        });


    //Enum Variables
    private String description;
    private boolean isFunction;
    private final Calculator operation;
    private int precedence; //tells parser which operator should be calculated first.
    private int argumentCount; //needed for things like sin() which need 1 argument while max() needs 2

    //Constructor
    private Operations(String description, int precedence, int argumentCount, boolean isFunction, Calculator operation){
        this.description = description;
        this.precedence = precedence;
        this.argumentCount = argumentCount;
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
        return this.operation;
    }
    public int getArgumentCount(){
        return this.argumentCount;
    }
    public int getPrecedence(){
        return this.precedence;
    }
    
    public static Operations fromSymbol(String symbol){
        for (Operations op: values()){
            if (op.description.equals(symbol)){
                return op;
            }
        }
        return null;
    }

    }
    //Lambda functions need a Functional Interface to interact with
    @FunctionalInterface
    interface Calculator {
        double operation(double num1, double num2);
    }

    static public ArrayList<String> Tokenizer(String userInput){
        
        ArrayList<String> tokenizerOutput = new ArrayList<>();
        StringBuilder temp = new StringBuilder();

       for(char c : userInput.toCharArray()){
            if (Character.isLetterOrDigit(c) || c == '.'){
                temp.append(c);
            }
            else{

                if (temp.length() > 0){
                    tokenizerOutput.add(temp.toString());
                    temp.setLength(0);
                }
                if (Character.isWhitespace(c)){
                    continue;
                }
                tokenizerOutput.add(String.valueOf(c));
            }
       } 
        
       if (temp.length() > 0){
        tokenizerOutput.add(temp.toString());
       }

        return tokenizerOutput;
    }

    //This algorithm was created using the link at the top
    static public ArrayList<String> SYA(ArrayList<String> tokens){

        ArrayList<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();

        for (String token: tokens){
            Operations operation = Operations.fromSymbol(token);

            switch (token){
                //If token is a number
                case String t when isNumber(t) || t.equalsIgnoreCase("pi") -> output.add(token);

                //Handling paraenthesis
                case "(" -> operators.push(token);

                
                case ")" -> {
                    while (!operators.empty() && !operators.peek().equals("(")){
                        output.add(operators.pop());
                    }

                    if (operators.empty()){
                        throw new IllegalArgumentException("Mismatched parentheses");
                    }

                    operators.pop();

                    if (!operators.empty()){
                        Operations top = Operations.fromSymbol(operators.peek());

                        if (top != null && top.getFunction()){
                            output.add(operators.pop());
                        }
                    }

                }

                case "," -> {
                    while (!operators.empty() && !operators.peek().equals("(")){
                        output.add(operators.pop());
                    }
                    if (operators.empty()){
                        throw new IllegalArgumentException("Misplaced comma");
                    }
                }

                //Main part of algorithm (functions and other things)
                default -> {

                    if (operation == null){
                        throw new IllegalArgumentException("Unknown token: " + token);
                    }

                    //Functions
                    if (operation.getFunction()){
                        operators.push(token);
                    }

                    //Operators

                    else {
                        while (!operators.empty()){
                            Operations top = Operations.fromSymbol(operators.peek());

                            if (top == null || top.getFunction()) break;

                            if (top.getPrecedence() > operation.getPrecedence() || (top.getPrecedence() == operation.getPrecedence() && !token.equals("^"))){

                                output.add(operators.pop());
                            }
                            else break;
                        }
                        operators.push(token);
                    }

                }
            }
        }
        
        while (!operators.empty()){
            String token = operators.pop();

            if (token.equals("(") || token.equals(")")){
                throw new IllegalArgumentException("Mismatched parentheses");
            }

            output.add(token);
        }
        
        return output;
    }

    //I couldn't figure this out so I used AI :(
    static public boolean isNumber(String token){
        try{
            Double.parseDouble(token);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public static double Evaluate(ArrayList<String> rpn) {

        Stack<Double> numbers = new Stack<>();

        for (String token : rpn) {

            Operations operation = Operations.fromSymbol(token);

            // If it isn't an operation, it's a number
            if (operation == null) {
                if (token.equalsIgnoreCase("pi")){
                    numbers.push(Math.PI);
                }
                else{
                    numbers.push(Double.parseDouble(token));
                }
                
                continue;
            }

            double num2 = 0;
            double num1 = numbers.pop();

            if (operation.getArgumentCount() == 2) {
                num2 = num1;
                num1 = numbers.pop();
            }

            double result = operation.operate().operation(num1, num2);

            numbers.push(result);
        }

        if (numbers.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        return numbers.pop();
    }

    static public void menuStartup(){

        System.out.println("=== Operations Menu ===");
        
        for (Operations operation : Operations.values()){

            System.out.print(operation.name() + ": " + operation.getDescription());

            if (operation.getDescription().equals("logb")){
                System.out.print("  (number, base)");
            }

            System.out.println();

        }
        System.out.println();
        System.out.println("Constants: pi");
        System.out.println("NOTE: Negative numbers are not implemented, so any time you want to use a negative number just do like '0-1'");
        System.out.println("Enter 'exit' (case insensitive) to terminate the program.  Any incorrect syntax/logic will also terminate with a message explaining why.");
        System.out.println("=====================");
        

    }
}
