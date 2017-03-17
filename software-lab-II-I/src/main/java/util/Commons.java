package util;

import java.util.Random;

public class Commons
{
    public static int getRandomValue(int[] arr)
    {
        int index = rand(0, arr.length - 1);
        return arr[index];
    }

    public static int[][] createMatrix(int n, int defaultValue)
    {
        int[][] d = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                d[i][j] = defaultValue;

        return d;
    }

    public static void writeMatrix(int[][] r)
    {
        int n = r.length;
        for (int i = 0; i < n; i++)

        {
            System.out.println("");
            for (int j = 0; j < n; j++)
            {
                System.out.print(r[i][j] + " ");

            }
        }
    }

    public static int rand(int min, int max)
    {
        int rand = new Random().nextInt((max - min) + 1) + min;
        return rand;
    }

    public static int random(int max)
    {
        return rand(0, max - 1);
    }

    public static void writeMatrix(double[][] r)
    {
        int n = r.length;
        for (int i = 0; i < n; i++)

        {
            System.out.println("");
            for (int j = 0; j < n; j++)
            {
                System.out.print(r[i][j] + " ");

            }
        }
    }

    public static int[] toIntArray(String[] sArray)
    {
        int[] array = new int[sArray.length];
        for (int i = 0; i < sArray.length; i++)
        {
            array[i] = Integer.parseInt(sArray[i]);
        }
        return array;
    }

    public double format(double number)
    {
        return Double.parseDouble(String.format("%.1f", number));
    }
}
