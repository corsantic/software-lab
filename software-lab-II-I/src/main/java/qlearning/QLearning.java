package qlearning;

import entity.Maze;
import util.Commons;

import static util.Commons.rand;

public class QLearning {
    static double Y = 0.8;

    public int[][] getRMatrix(Maze maze) {
        int n = maze.getN();

        int[][] rMatrix = Commons.createMatrix(n, -1);
        System.out.println("   ---- " + n);

        maze.getNeighbours().forEach((i, neigs) -> {
            for (int j : neigs) {
                rMatrix[i][j] = 0;
            }
        });

        rMatrix[maze.getEndPoint()][maze.getEndPoint()] = 100;
        for (int j : maze.getNeighbours().get(maze.getEndPoint())) {
            rMatrix[j][maze.getEndPoint()] = 100;
        }

        return rMatrix;
    }

    /**
     * Q(durum,aksiyon) = R(durum,aksiyon)+γ×Max{Q(sonrakidurumlar,tumaksiyonlar)}
     * γ  ̈o ̆grenme katsayısıdır ve 0 ile 1 arasında bir de ̆ger alır.
     */
    public double[][] buildQMatrix(Maze maze) {
        double[][] Q = new double[maze.getN()][maze.getN()];

        int[][] R = getRMatrix(maze);
        int x = maze.getStartPoint(), y;

        int i = 0;
        while (i < maze.getIterationCount()) {
            int[] xNeighbours = maze.getNeighbours().get(x);
            y = xNeighbours[rand(0, xNeighbours.length - 1)];

            Q[x][y] = getNextQPoint(Q, R, x, y, maze);

            x = y;
            i++;
        }

        return Q;
    }

    private double getNextQPoint(double[][] Q, int[][] R, int x, int y, Maze maze) {
        double max = -4;
        for (int yNeigbour : maze.getNeighbours().get(y)) {
            max = Math.max(max, Q[y][yNeigbour]);
        }

        double num = R[x][y] + (Y * max);
//        Double.parseDouble(String.format("%.1f", num))
        return num;
    }

    // todo:
    public int[] findPath(Maze maze) {

        return new int[5];
    }

}
