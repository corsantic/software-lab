package qlearning;

import java.util.Map;
import java.util.Random;

public class QLearning {
    static double Y = 0.8;

    public static int rand(int min, int max) {
        int rand = new Random().nextInt((max - min) + 1) + min;
        return rand;
    }

    public int[][] getRMatrix(int n, int end, Map<Integer, int[]> neighbours) {
        int[][] rMatrix = dump(n);

        neighbours.forEach((i, neigs) -> {
            for (int j : neigs) {
                rMatrix[i][j] = 0;
            }
        });

        rMatrix[end][end] = 100;
        for (int j : neighbours.get(end)) {
            rMatrix[j][end] = 100;
        }

        return rMatrix;
    }

    int[][] dump(int n) {
        int[][] d = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                d[i][j] = -1;

        return d;
    }

    /**
     * Q(durum,aksiyon) = R(durum,aksiyon)+γ×Max{Q(sonrakidurumlar,tumaksiyonlar)}
     * γ  ̈o ̆grenme katsayısıdır ve 0 ile 1 arasında bir de ̆ger alır.
     */
    public double[][] buildQMatrix(Map<Integer, int[]> neighbours, int iterationCount, int start, int end, int n) {
        double[][] q = new double[n][n];

        int[][] r = getRMatrix(n, end, neighbours);
        int x = start, y;

        int i = 0;
        while (i < iterationCount) {
            int[] neigs = neighbours.get(x);
            y = neigs[rand(0, neigs.length - 1)];

            q[x][y] = getNextQPoint(x, y, r, q, neighbours.get(y));
            x = y;
            i++;
        }

        return q;
    }

    private double getNextQPoint(int x, int y, int[][] r, double[][] q, int... yNeigbours) {
        double max = 0;
        if (null != yNeigbours) {
            for (int yNeigbour : yNeigbours) {
                max = Math.max(max, q[y][yNeigbour]);
            }
        }
//        System.out.println("x:" + x + " y:" + y);
        return r[x][y] + (Y * max); // todo: (int) cast ?
    }

}
