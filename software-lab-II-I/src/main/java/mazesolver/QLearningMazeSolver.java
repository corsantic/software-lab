package mazesolver;

import static util.Commons.random;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entity.Maze;
import util.Commons;

public class QLearningMazeSolver
{
    private static double GAMMA = 0.8;
    public double Q[][];
    public int R[][];
    public List<Integer> path;


    public void initialize(Maze maze)
    {
        buildRMatrix(maze);
        buildQMatrix(maze);
        buildPath(maze);
    }


    private void buildPath(Maze maze)
    {
        int startPoint = maze.getStartPoint();
        List<Integer> path = new ArrayList<>();
        path.add(startPoint);

        while (startPoint != maze.getEndPoint())
        {
            int nextStep = findNextPoint(maze, startPoint);

            if (nextStep == -1)
                break;

            path.add(nextStep);
            startPoint = nextStep;
        }

        this.path = path;
    }


    private void buildRMatrix(Maze maze)
    {
        int n = maze.getN();
        R = Commons.createMatrix(n, -1);

        maze.getNeighbours().forEach((neighbours) ->
        {
            for (int j : neighbours)
            {
                int index = maze.getNeighbours().indexOf(neighbours);
                R[index][j] = 0;
            }
        });

        R[maze.getEndPoint()][maze.getEndPoint()] = 100;
        for (int j : maze.getNeigboursFromPoint(maze.getEndPoint()))
        {
            R[j][maze.getEndPoint()] = 100;
        }
    }

    /**
     * Q(durum,aksiyon) = R(durum,aksiyon)+γ×Max{Q(sonrakidurumlar,tumaksiyonlar)}
     * γ  ögrenme katsayısıdır ve 0 ile 1 arasında bir de ̆ger alır.
     */
    private void buildQMatrix(Maze maze)
    {
        Q = new double[maze.getN()][maze.getN()];

        int x = random(maze.getN()); // random start point

        fillQ(x, maze);
    }

    private void fillQ(int x, Maze maze)
    {
        for (int i = 0; i < maze.getIterationCount(); i++)
        {
            int y = 0;
//            while (R[x][y] != 100)
//            {
                int[] xNeighbours = maze.getNeigboursFromPoint(x);
                y = Commons.getRandomValue(xNeighbours);
                double yNeighbourWithMaxGain = findNeighbourWithMaxGain(maze, y);
                double num = R[x][y] + GAMMA * yNeighbourWithMaxGain;
                Q[x][y] = num;

                x = y;
//            }
            //            writeMatrix(Q);
        }
    }


    private double findNeighbourWithMaxGain(Maze maze,  int y)
    {
        return Arrays.stream(maze.getNeigboursFromPoint(y))
                .mapToDouble(n -> Q[y][n])
                .max().getAsDouble();
    }

    private int findNextPoint(Maze maze, int currentPoint)
    {
        int[] neighbours = maze.getNeigboursFromPoint(currentPoint);

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

    public void exportFiles(Maze m) throws IOException
    {
        FileWriter writeR = new FileWriter("outR.txt", false);

        int size = m.getN();
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                writeR.write(Integer.toString(R[i][j]));
                writeR.write(" , \t");
            }
            writeR.write("\r\n");

        }
        writeR.close();
        FileWriter writeQ = new FileWriter("outQ.txt", false);
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                writeQ.write(Commons.format(Q[i][j]));
                writeQ.write(" , \t");
            }
            writeQ.write("\r\n");

        }
        writeQ.close();

        FileWriter pathWriter = new FileWriter("outPath.txt", false);
        pathWriter.write(path.toString());
        pathWriter.close();

    }
}

