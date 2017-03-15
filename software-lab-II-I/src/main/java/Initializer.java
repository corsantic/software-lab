import static util.Commons.toIntArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import entity.Maze;
import qlearning.QLearning;


public class Initializer
{
    private static final String FILE_NAME = "inputs/input22.txt";

    private QLearning qLearning = new QLearning();

    public static void main(String[] args) throws IOException
    {
        new Initializer().run(args);
    }

    void run(String[] args) throws IOException
    {
             Maze maze = buildLab();
//        Maze maze = Constants.INPUT_33;
//        int[][] rMatrix = qLearning.getRMatrix(maze);
//        writeMatrix(rMatrix);
//
//        System.out.println("-----------------------");
//        System.out.println("------- Q Matrix ---------\n");
//        double[][] qMatrix = qLearning.buildQMatrix(maze);
//
//        writeMatrix(qMatrix);
//
//        System.out.println(qLearning.findPath(maze));

    }


    private Maze buildLab() throws IOException
    {
        String filePath = getFilePath(FILE_NAME);
        BufferedReader reader = new BufferedReader(new FileReader(
                filePath));
        while (true)
        {
            String line = reader.readLine();
            if (line == null)
            {
                break;
            }
            // Split line on comma.
            String[] parts = line.split(",");
            for (String part : parts)
            {
                System.out.print(part+",");
            }
            System.out.println();
        }

        reader.close();


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

