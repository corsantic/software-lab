
import java.util.Scanner;

public class QLearningInitializer {


    public static void main(String[] args) {


        new QLearningInitializer().run(args);


    }

    void run(String[] args) {
        System.out.println("......");
        System.out.print("Matris boyutu:");
        Scanner input = new Scanner(System.in);
//        System.out.print("beginning room: ");
//
//        String out=input.nextLine();
//        System.out.print("target room:");
//        String in=input.nextLine();


        int n = input.nextInt();
        int[][] R = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.out.print(i+". neighbours:");
            int nb=input.nextInt();
            for (int j = 0; j < n; j++) {



                R[i][j] = -1;
                if (nb==j)
                {
                    R[i][j]=0;

                }


            }

        }
        System.out.print("R matrix:");
        for (int i = 0; i < n; i++)

        {
            System.out.println("");
            for (int j = 0; j < n; j++) {
                System.out.print(R[i][j] + " ");

            }


        }
    }


}

