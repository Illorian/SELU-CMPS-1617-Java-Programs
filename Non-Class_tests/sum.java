import java.util.Scanner;

public class sum {
    public static void main (String args[]){

        Scanner input = new Scanner(System.in);
        System.out.println("Input start range: ");
        int startRange = input.nextInt();
        System.out.println("Input end range: ");
        int endRange = input.nextInt();


        System.out.print("Sum is: " + summation(startRange - 1, endRange));

        input.close();
    }

    public static int summation(int startInt, int endInt){
        
        int overApprox = (endInt * (endInt + 1)) / 2;
        int underApprox = (startInt * (startInt + 1)) / 2;

        return overApprox - underApprox;
    }
}
