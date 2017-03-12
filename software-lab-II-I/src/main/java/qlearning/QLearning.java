package qlearning;

import java.util.Map;
import java.util.Random;

public class QLearning
{
    static double Y = 0.8;

    public int[][] getRMatrix(int n, int start, int end, Map<Integer, int[]> neighbours)
    {
        int[][] rMatrix = new int[n][n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                rMatrix[i][j] = -1;
                for (int neig : neighbours.get(i))
                {
                    if (j == neig)
                    {
                        rMatrix[i][j] = 0;
                    }
                }
            }
        }

        // give target neighbours 100

        return rMatrix;
    }

    /**
     * Q(durum,aksiyon) = R(durum,aksiyon)+γ×Max{Q(sonrakidurumlar,tumaksiyonlar)}
     * γ  ̈o ̆grenme katsayısıdır ve 0 ile 1 arasında bir de ̆ger alır.
     */
    public int[][] buildQMatrix(Map<Integer, int[]> neighbours, int iterationCount, int start, int end, int n)
    {
        int[][] q = new int[n][n];
        
        int[][] r = getRMatrix(n, start, end, neighbours);
        int x = start, y;

        int i = 0;
        while (i < iterationCount)
        {
            int[] neigs = neighbours.get(start);
            y = rand(0, neigs.length);

            int[] yNeigbours = neighbours.get(y);

            q[x][y] = getNextQPoint(x, y, r, q, yNeigbours);

            i++;
        }

        return q;
    }

    private int getNextQPoint(int x, int y, int[][] r, int[][] q, int... yNeigbours)
    {
        int max = 0;
        if (null != yNeigbours)
        {
            for (int yNeigbour : yNeigbours)
            {
                System.out.println("y:" + y + " yN:" + yNeigbour);
                max = Math.max(max, q[y][yNeigbour]);
            }
        }
        System.out.println("x:" + x + " y:" + y);
        return r[x][y] + (int) (Y * max); // todo: (int) cast ?
    }

    public static Number max(int... numbers)
    {
        Number max = 0;
        for (Number num : numbers)
        {
            max = Math.max(num.doubleValue(), max.doubleValue());
        }
        return max;
    }

    public static int rand(int min, int max)
    {
        int rand = new Random().nextInt((max - min) + 1) + min;
        return rand;
    }

}
