/*

Brett Core
Program 6
10/22/26
CMPS 1617

This program finds integers that are divisible by 5 and 6 in the range 100-1000
*/


public class Program_6 {
    public static void main(String args[]){

        final int startRange = 100;
        final int endRange = 1000;
        int count = 0;
        
        for (int i = startRange; i <= endRange; i++){

            if (i % 5 == 0 && i % 6 == 0){
                System.out.print(i + " ");
                count++;

                if (count % 10 == 0){
                    System.out.print("\n");
            }
            }

            
        }



    }
    
}
