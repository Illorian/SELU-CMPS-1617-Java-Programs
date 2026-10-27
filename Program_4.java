/*

Brett Core
Program 4
9/23/26
CMPS 1617

This program generates an integer 1-12, then maps that to a month.
*/


import java.util.Random;
import java.lang.Math;

public class Program_4
{
        public static void main(String[] args)
    {

        Random generator = new Random();
        
        int number = generator.nextInt();
        number = (Math.abs(number % 12)) + 1; // nextInt() gives negative so we need absolute value

        System.out.println("Your randomly generator number is: " + number);

        switch(number){
            case 1: System.out.println("Month: January"); break;
            case 2: System.out.println("Month: February"); break;
            case 3: System.out.println("Month: March"); break;
            case 4: System.out.println("Month: April"); break;
            case 5: System.out.println("Month: May"); break;
            case 6: System.out.println("Month: June"); break;
            case 7: System.out.println("Month: July"); break;
            case 8: System.out.println("Month: August"); break;
            case 9: System.out.println("Month: September"); break;
            case 10: System.out.println("Month: October"); break;
            case 11: System.out.println("Month: November"); break;
            case 12: System.out.println("Month: December"); break;
            default: System.out.println("ERROR: not a number 1 - 12"); break; //just in case
        }

    }

}