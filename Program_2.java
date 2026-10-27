/*

Brett Core
9/3/2026
CMPS 1610 - H
Program 2

*/


import java.util.Scanner;

public class Program_2
{
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        System.out.println("Enter a temperature in Celsius: ");

        double Celsius = input.nextDouble();

        double Fahrenheit = (Celsius * (9.0/5.0)) + 32.0;

        System.out.println("Your temperature in Fahrenhiet is: " + Fahrenheit);

        input.close();

    }
}
