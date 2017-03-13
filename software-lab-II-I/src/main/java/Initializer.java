import static util.Commons.toIntArray;
import static util.Commons.writeMatrix;

import java.io.File;
import java.util.Scanner;

import entity.Maze;
import qlearning.QLearning;
import util.Constants;


public class Initializer
{
    private static final String FILE_NAME = "inputs/input22.txt";

    private QLearning qLearning = new QLearning();

    public static void main(String[] args)
    {
        new Initializer().run(args);
    }

    void run(String[] args)
    {
        //        Maze maze = buildLab();
        Maze maze = Constants.INPUT_22;
        int[][] rMatrix = qLearning.getRMatrix(maze);
        writeMatrix(rMatrix);

        System.out.println("-----------------------");
        System.out.println("------- Q Matrix ---------\n");
        double[][] qMatrix = qLearning.buildQMatrix(maze);

        writeMatrix(qMatrix);

    }


    private Maze buildLab()
    {
        String filePath = getFilePath(FILE_NAME);
        File file = new File(filePath); // read neigbours

        Scanner input = new Scanner(System.in);
        System.out.print("matrix size: ");
        int n = input.nextInt();
        Maze maze = new Maze(n);
        input.nextLine(); //tmp

        for (int i = 0; i < n * n; i++)
        {
            System.out.print(i + ". neighbours: ");
            String nb = input.nextLine();
            maze.getNeighbours().put(i, toIntArray(nb.split(",")));
        }

        System.out.print("start: ");
        maze.setStartPoint(input.nextInt());

        System.out.print("end: ");
        maze.setEndPoint(input.nextInt());

        System.out.print("iterationCount: ");
        maze.setIterationCount(input.nextInt());

        System.out.println("builded maze: " + maze.toString());
        return maze;
    }

    private String getFilePath(String filename)
    {
        ClassLoader loader = getClass().getClassLoader();
        return loader.getResource(filename).getFile();
    }
}

