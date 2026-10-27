/*

Brett Core
9/10/2026
CMPS 1610 - H
Program 3

*/


import java.util.Scanner;

public class Program_3
{
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        char asciiCharacter;

        boolean isNumber = false;
        String value = "";
        while(!isNumber){
            isNumber = true;
            System.out.println("Input the integer (0 - 128) you want converted into ASCII: ");
            value = input.nextLine();
            for (char c : value.toCharArray()){
                isNumber &= (c >= '0') & (c <= '9');
            }
        }
        int asciiCode = Integer.parseInt(value);

        asciiCharacter = (char)asciiCode;

        System.out.println("Your ASCII character is: " + asciiCharacter);

        input.close();
    }
}
