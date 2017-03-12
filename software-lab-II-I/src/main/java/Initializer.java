
import java.util.Scanner;

import entity.Lab;
import qlearning.QLearning;


public class Initializer
{
    QLearning qLearning = new QLearning();

    public static void main(String[] args)
    {
        new Initializer().run(args);
    }

    void run(String[] args)
    {
//        Lab lab = buildLab();
        Lab lab = buildDummyLab();
        int[][] rMatrix = qLearning.getRMatrix(lab.getN(), lab.getStartPoint(), lab.getEndPoint(), lab.getNeighbours());
        writeMatrix(lab.getN(), rMatrix);


        int[][] qMatrix = qLearning.buildQMatrix(lab.getNeighbours(), lab.getIterationCount(), lab.getStartPoint(), lab.getEndPoint(), lab.getN());

        writeMatrix(lab.getN(), qMatrix);

    }

    private Lab buildDummyLab()
    {
        Lab lab = new Lab(2);

        lab.getNeighbours().put(0, new int[]{1, 2});
        lab.getNeighbours().put(1, new int[]{0, 3});
        lab.getNeighbours().put(2, new int[]{0});
        lab.getNeighbours().put(3, new int[]{1});

        lab.setStartPoint(0);
        lab.setEndPoint(3);
        lab.setIterationCount(5);

        return lab;
    }

    private Lab buildLab()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("matrix size: ");
        int n = input.nextInt();
        Lab lab = new Lab(n);
        input.nextLine(); //tmp

        for (int i = 0; i < n * n; i++)
        {
            System.out.print(i + ". neighbours: ");
            String nb = input.nextLine();
            lab.getNeighbours().put(i, toIntArray(nb.split(",")));
        }

        System.out.print("start: ");
        lab.setStartPoint(input.nextInt());

        System.out.print("end: ");
        lab.setEndPoint(input.nextInt());

        System.out.print("iterationCount: ");
        lab.setIterationCount(input.nextInt());

        System.out.println("builded lab: " + lab.toString());
        return lab;
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

    private int[] toIntArray(String[] sArray)
    {
        int[] array = new int[sArray.length];
        for (int i = 0; i < sArray.length; i++)
        {
            array[i] = Integer.parseInt(sArray[i]);
        }
        return array;
    }

}

