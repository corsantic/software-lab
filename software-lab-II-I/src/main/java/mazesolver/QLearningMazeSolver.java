package mazesolver;

import static util.Commons.random;

import java.util.ArrayList;
import java.util.List;

import entity.Maze;
import util.Commons;

public class QLearningMazeSolver implements MazeSolver
{
    private static double GAMMA = 0.8;

    @Override
    public List<Integer> findPath(Maze maze)
    {
        int startPoint = maze.getStartPoint();
        List<Integer> path = new ArrayList<>();
        path.add(startPoint);

        double[][] Q = buildQMatrix(maze);

        while (startPoint != maze.getEndPoint())
        {
            int nextStep = findNextPoint(maze, Q, startPoint);
            path.add(nextStep);
            startPoint = nextStep;
        }

        return path;
    }

    public int[][] buildRMatrix(Maze maze)
    {
        int n = maze.getN();
        int[][] R = Commons.createMatrix(n, -1);

        maze.getNeighbours().forEach((neighbours) ->
        {
            for (int j : neighbours)
            {
                int index = maze.getNeighbours().indexOf(neighbours);
                R[index][j] = 0;
            }
        });

        R[maze.getEndPoint()][maze.getEndPoint()] = 100;
        for (int j : maze.getNeighbours().get(maze.getEndPoint()))
        {
            R[j][maze.getEndPoint()] = 100;
        }

        return R;
    }

    /**
     * Q(durum,aksiyon) = R(durum,aksiyon)+γ×Max{Q(sonrakidurumlar,tumaksiyonlar)}
     * γ  ögrenme katsayısıdır ve 0 ile 1 arasında bir de ̆ger alır.
     */
    public double[][] buildQMatrix(Maze maze)
    {
        int[][] R = buildRMatrix(maze);
        double[][] Q = new double[maze.getN()][maze.getN()];

        int x = random(maze.getN()); // random start point

        fillQ(Q, R, x, maze, maze.getIterationCount());

        return Q;
    }

    private void fillQ(double[][] Q, int[][] R, int x, Maze maze, int it)
    {

        for (int i = 0; i < maze.getIterationCount(); i++)
        {
            int[] xNeighbours = maze.getNeighbours().get(x);
            int y = Commons.getRandomValue(xNeighbours);
            double max = findNeighbourWithMaxGain(maze, Q, y);
            double num = R[x][y] + GAMMA * max;
            Q[x][y] = num;

            x = y;
        }

    }

    private double findNeighbourWithMaxGain(Maze maze, double[][] Q, int y)
    {
        double max = Double.MIN_VALUE;
        for (int n : maze.getNeighbours().get(y))
        {
            max = Math.max(max, Q[y][n]);
        }
        return max;
    }


    private int findNextPoint(Maze maze, double[][] Q, int currentPoint)
    {
        int[] neighbours = maze.getNeighbours().get(currentPoint);
        double max = Double.MIN_VALUE;
        int nextStep = -1;
        for (int n : neighbours)
        {
            double gain = Q[currentPoint][n];
            if (gain >= max)
            {
                nextStep = n;
                max = gain;
            }
        }

        return nextStep;
    }

    public void exportFiles(Maze m)
    {

    }
}
