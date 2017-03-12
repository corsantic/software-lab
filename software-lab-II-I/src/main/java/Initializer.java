import entity.Maze;
import qlearning.QLearning;
import util.Constants;

import java.util.Scanner;

import static util.Commons.toIntArray;
import static util.Commons.writeMatrix;


public class Initializer {
    QLearning qLearning = new QLearning();

    public static void main(String[] args) {
        new Initializer().run(args);
    }

    void run(String[] args) {
//        Maze maze = buildLab();
        Maze maze = Constants.INPUT_33;
        int[][] rMatrix = qLearning.getRMatrix(maze);
        writeMatrix(rMatrix);

        System.out.println("-----------------------");
        System.out.println("------- Q Matrix ---------\n");
        double[][] qMatrix = qLearning.buildQMatrix(maze);

        writeMatrix(qMatrix);

    }


    private Maze buildLab() {
        Scanner input = new Scanner(System.in);
        System.out.print("matrix size: ");
        int n = input.nextInt();
        Maze maze = new Maze(n);
        input.nextLine(); //tmp

        for (int i = 0; i < n * n; i++) {
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


}

