/*

Brett Core
Program 8
11/4/2026
CMPS 1617

This program generates 100 random integers 0-9, displays them, and then displays the frequency of each.
*/


import java.util.Random;

public class Program_8 {
    public static void main(String args[]){
        //Variables
        Random generator = new Random();

        int amountGenerated = 100;
        int randomNumbers[] = new int[amountGenerated];
        int numberCount[] = new int[10]; 
        //For some reason I found out that if you assign slots/memory to an array of numbers,
        //it initalizes it as 0 which makes it so you don't have to check if the slot is null :)
         
        for (int i = 0; i < amountGenerated; i++){
            //Generating the numbers and displaying
            randomNumbers[i] = generator.nextInt(10);
            if (i % 10 == 0){
                System.out.println();
            }
            System.out.print(randomNumbers[i] + " ");

            //Recording the type of integer
            numberCount[randomNumbers[i]] += 1;
        }

        //Outputting the counts of each integer
        System.out.println();
        
        for (int i = 0; i < 10; i++){
            System.out.println("The total amount of the number " + i + " is: " + numberCount[i]);
        }

    }
}
