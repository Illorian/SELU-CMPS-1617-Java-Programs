/*

Brett Core
Program 7
10/27/26
CMPS 1617

This program gets an integer number from the user and then outputs that number reversed.
*/

import java.util.Scanner;

public class Program_7{
    public static void main(String args[]){

        Scanner input = new Scanner(System.in);
        System.out.print("Input a integer: ");
        
        int inputNumber = input.nextInt();
        System.out.println();

        reverse(inputNumber);

        input.close();

    }


    public static void reverse(int num1){
        System.out.print("Your reversed integer is: ");
        while (num1 > 0){
            System.out.print(num1 % 10);
            num1 = num1 / 10;
        }
    }
}