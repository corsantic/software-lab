
import java.util.Scanner;

import util.CommonUtils;

public class QLearningInitializer
{


    public static void main(String[] args)
    {
        new QLearningInitializer().run(args);
    }

    void run(String[] args)
    {
        // degiskenler


        // readInputs


        // r matris olustur


        // q olutur

        // matris yaz


        System.out.print("Matris boyutu:");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[][] R = new int[n][n];

        input.nextLine();

        readNeighbours(input, n, R);


        writeMatrix(n, R);

        inputStartEndAndIteration(input);
        //        Q(durum; aksiyon) = R(durum; aksiyon)+
        //MaxfQ(sonrakidurumlar; tumaksiyonlar)g


    }

    private void inputStartEndAndIteration(Scanner input)
    {
        int start, end, iterationCount;
        System.out.println("");
        System.out.println("start:");
        start = input.nextInt();
        System.out.println("end: ");
        end = input.nextInt();
        System.out.println("iterationCount: ");
        iterationCount = input.nextInt();
    }

    private void writeMatrix(int n, int[][] r)
    {
        System.out.print("R matrix:");
        for (int i = 0; i < n; i++)

        {
            System.out.println("");
            for (int j = 0; j < n; j++)
            {
                System.out.print(r[i][j] + " ");

            }
        }
    }

    private void readNeighbours(Scanner input, int n, int[][] r)
    {
        for (int i = 0; i < n; i++)
        {
            System.out.print(i + ". neighbours:");
            String nb = input.nextLine();
            String[] split = nb.split(",");

            for (int j = 0; j < n; j++)
            {
                r[i][j] = -1;
                for (String neig : split)
                {
                    if (CommonUtils.isNotEmpty(neig) && Integer.valueOf(neig).equals(j))
                    {
                        r[i][j] = 0;
                    }
                }
            }
        }
    }


}

