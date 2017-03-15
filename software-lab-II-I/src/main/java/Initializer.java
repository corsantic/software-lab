import static util.Commons.toIntArray;
import static util.Commons.writeMatrix;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import entity.Maze;
import mazesolver.QLearningMazeSolver;


public class Initializer
{
    // input33 - start: 1 end: 7
    // input55 - start: 2 end: 22
    private final int MATRIX_SIZE = 20;
    private final Color LIGHT_BLUE = new Color(176, 213, 255);
    private final Color DARK_BLUE = new Color(41, 80, 160);
    private final Color GRAY = new Color(117, 117, 117);
    private Color DEFAULT_BACKGROUND_COLOR = new Color(238, 238, 238);
    private static final String FILE_NAME = "inputs/input55.txt";

    private QLearningMazeSolver qLearningMazeSolver = new QLearningMazeSolver();

    public static void main(String[] args) throws Exception
    {
        new Initializer().run(args);
    }

    void run(String[] args) throws Exception
    {

        List<int[]> neighbours = readNeighboursFromFile(FILE_NAME);
        Maze maze = new Maze();
        maze.setNeighbours(neighbours);
        maze.setN(neighbours.size());
        //        maze.setN((int) Math.sqrt(neighbours.size()));

        maze.setStartPoint(2);
        maze.setEndPoint(22);
        maze.setIterationCount(3000);

        GridBagConstraints gridBagConstraints = createConstraints();
        final JFrame frame = new JFrame("Maze Solver");
        final JPanel vertexPanel = createVertexPanel(maze);


        frame.setLayout(new GridBagLayout());

        gridBagConstraints.anchor = GridBagConstraints.CENTER;

        gridBagConstraints.gridy++;
        frame.add(vertexPanel, gridBagConstraints);
        gridBagConstraints.gridy++;
        gridBagConstraints.gridwidth++;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        frame.add(vertexPanel, gridBagConstraints);
        gridBagConstraints.gridy++;

        frame.pack();
        frame.setVisible(true);

    }


    private JPanel createVertexPanel(Maze maze)
    {
        int size = (int) Math.sqrt(maze.getN());

        List<Integer> path = qLearningMazeSolver.findPath(maze);

        JPanel vertexPanel = new JPanel();
        vertexPanel.setLayout(new GridLayout(size, size, 0, 0));
        vertexPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        for (int i = 0; i < maze.getN(); i++)
        {
            int[] neighbours = maze.getNeighbours().get(i);

            vertexPanel.add(createPoint(size, i, neighbours, path.contains(i), i == maze.getStartPoint(), i == maze.getEndPoint()), gridBagConstraints);
        }


        return vertexPanel;
    }

    private JButton createPoint(int n, int point, int[] neighbours, boolean path, boolean start, boolean end)
    {

        int borderThickness = 3;
        int left = start || contain(point - 1, neighbours) ? 0 : borderThickness ;
        int right = end || contain(point + 1, neighbours) ? 0 : borderThickness ;
        int bottom = end || contain(point + n, neighbours) ? 0 : borderThickness ;
        int top = start || contain(point - n, neighbours) ? 0 : borderThickness ;


        Font font = new Font("Helvetica", Font.PLAIN, 30);

        Border border = BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK);


        JButton po = new JButton(path ? "*" : "");
        po.setFont(font);
        po.setPreferredSize(new Dimension(100,100));
        po.setForeground(DARK_BLUE);
        po.setHorizontalAlignment(JLabel.CENTER);
        po.setBorder(border);
        return po;
    }

    private boolean contain(int x, int[] vals)
    {
        for (int val : vals)
        {
            if(val == x)
                return true;
        }
        return false;
    }


    private GridBagConstraints createConstraints()
    {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.fill = GridBagConstraints.ABOVE_BASELINE;
        return gridBagConstraints;
    }

    void test() throws Exception
    {
        //        Maze maze = buildLab();
        List<int[]> neighbours = readNeighboursFromFile(FILE_NAME);
        Maze maze = new Maze();
        maze.setNeighbours(neighbours);
        maze.setN(neighbours.size());
        //        maze.setN((int) Math.sqrt(neighbours.size()));

        maze.setStartPoint(1);
        maze.setEndPoint(7);
        maze.setIterationCount(3000);


        int[][] rMatrix = qLearningMazeSolver.buildRMatrix(maze);
        writeMatrix(rMatrix);

        System.out.println("-----------------------");
        System.out.println("------- Q Matrix ---------\n");
        double[][] qMatrix = qLearningMazeSolver.buildQMatrix(maze);

        writeMatrix(qMatrix);
        System.out.println("------- Q Matrix ---------\n");

        System.out.println(qLearningMazeSolver.findPath(maze));

    }

    private List<int[]> readNeighboursFromFile(String fileName) throws FileNotFoundException
    {
        List<int[]> neigbours = new ArrayList<>();
        File file = new File(getFilePath(fileName));

        Scanner read = new Scanner(file);

        while (read.hasNextLine())
        {
            String[] neigs = read.nextLine().split(",");
            neigbours.add(toIntArray(neigs));
        }

        return neigbours;
    }


    private String getFilePath(String filename)
    {
        ClassLoader loader = getClass().getClassLoader();
        return loader.getResource(filename).getFile();
    }
}

