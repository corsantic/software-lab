package qlearning;

import java.util.Map;

import static util.Commons.rand;

public class QLearning {
    static double Y = 0.80;

    public int[][] getRMatrix(int n, int end, Map<Integer, int[]> neighbours) {
        int[][] rMatrix = dump(n);
        System.out.println("   ---- " + n);

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
            int[] yneigs = neighbours.get(y);

            q[x][y] = getNextQPoint(x, y, r, q, yneigs);
            x = y;
            i++;
        }

        return q;
    }

    private double getNextQPoint(int x, int y, int[][] r, double[][] q, int... yNeigbours) {
        double max = -4;
        if (null != yNeigbours) {
            for (int yNeigbour : yNeigbours) {
                max = Math.max(max, q[y][yNeigbour]);
            }
        }
//        System.out.println("x:" + Y * max);
        double num = r[x][y] + (Y * max);
        System.out.format("\nr[x][y] : %s + (Y * max) : %s  == %s", r[x][y], Y * max, num);

        return Double.parseDouble(String.format("%.1f", num));
    }

}
