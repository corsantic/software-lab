import static util.Commons.toIntArray;
import static util.Commons.writeMatrix;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import entity.Maze;
import mazesolver.QLearningMazeSolver;
import util.UIHelper;


public class Initializer
{
    private static final String FILE_NAME = "inputs/input55.txt";

    private final Color DARK_BLUE = new Color(41, 80, 160);
    private QLearningMazeSolver qLearningMazeSolver = new QLearningMazeSolver();
    private UIHelper uiHelper = new UIHelper();

    public static void main(String[] args) throws Exception
    {
        new Initializer().test(args);
    }

    void test(String[] args) throws Exception
    {
        Maze maze = createMaze(2, 22, 3000);

        qLearningMazeSolver.initialize(maze);

        int[][] ints = qLearningMazeSolver.R;
        double[][] doubles = qLearningMazeSolver.Q;


        writeMatrix(ints);
        writeMatrix(doubles);


        System.out.println("\n" + qLearningMazeSolver.path);

        qLearningMazeSolver.exportFiles(maze);
    }

    void run(String[] args) throws Exception
    {

        JFrame frame = uiHelper.createStandardJFrame();
        JPanel vertexPanel = createMainPanel();

        frame.add(vertexPanel);

        frame.pack();
        frame.setVisible(true);
    }


    private JPanel createMainPanel()
    {
        JPanel panel = new JPanel();

        JTextField start = uiHelper.createInput("start");
        JTextField end = uiHelper.createInput("end");
        JTextField itera = uiHelper.createInput("iteration");


        JButton submit = uiHelper.createStandardButton("Solve");
        JButton exportFiles = uiHelper.createStandardButton("Export Files");
        submit.addActionListener(evt ->
        {
            int s = Integer.valueOf(start.getText());
            int e = Integer.valueOf(end.getText());
            int i = Integer.valueOf(itera.getText());

            try
            {
                drawMaze(s, e, i);
            }
            catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
        });

        exportFiles.addActionListener(evt ->
        {
            int s = Integer.valueOf(start.getText());
            int e = Integer.valueOf(end.getText());
            int i = Integer.valueOf(itera.getText());


            try
            {
                qLearningMazeSolver.exportFiles(createMaze(s, e, i));
                JOptionPane.showMessageDialog(null, "Files exported.");

            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        });

        panel.add(start);
        panel.add(end);
        panel.add(itera);
        panel.add(submit);
        panel.add(exportFiles);

        return panel;
    }


    private void drawMaze(int start, int end, int iteration) throws FileNotFoundException
    {
        Maze maze = createMaze(start, end, iteration);
        JFrame frame = uiHelper.createStandardJFrame();

        JPanel vertexPanel = createVertexPanel(maze);
        frame.add(vertexPanel);

        frame.pack();
        frame.setVisible(true);
    }


    private Maze createMaze(int start, int end, int iteration)
    {
        List<int[]> neighbours = null;
        try
        {
            neighbours = readNeighboursFromFile(FILE_NAME);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        Maze maze = new Maze();
        maze.setNeighbours(neighbours);
        maze.setN(neighbours.size());

        maze.setStartPoint(start);
        maze.setEndPoint(end);
        maze.setIterationCount(iteration);
        return maze;
    }


    private JPanel createVertexPanel(Maze maze)
    {
        qLearningMazeSolver.initialize(maze);
        List<Integer> path = qLearningMazeSolver.path;
        int size = (int) Math.sqrt(maze.getN());

        JPanel mazePanel = new JPanel();
        mazePanel.setPreferredSize(new Dimension(600, 600));
        mazePanel.setLayout(new GridLayout(size, size, 0, 0));
        mazePanel.setBorder(new EmptyBorder(0, 20, 0, 20));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        for (int i = 0; i < maze.getN(); i++)
        {
            int[] neighbours = maze.getNeighbours().get(i);

            mazePanel.add(createPoint(size, i, neighbours, path.contains(i), i == maze.getStartPoint(), i == maze.getEndPoint()), gridBagConstraints);
        }

        return mazePanel;
    }

    private JButton createPoint(int n, int point, int[] neighbours, boolean path, boolean start, boolean end)
    {

        int borderThickness = 3;
        int left = start || contain(point - 1, neighbours) ? 0 : borderThickness;
        int right = end || contain(point + 1, neighbours) ? 0 : borderThickness;
        int bottom = end || contain(point + n, neighbours) ? 0 : borderThickness;
        int top = start || contain(point - n, neighbours) ? 0 : borderThickness;


        Font font = new Font("Helvetica", Font.PLAIN, 30);

        Border border = BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK);


        JButton po = new JButton(path ? "*" : "");
        po.setFont(font);
        po.setPreferredSize(new Dimension(100, 100));
        po.setForeground(DARK_BLUE);
        po.setHorizontalAlignment(JLabel.CENTER);
        po.setBorder(border);
        return po;
    }

    private boolean contain(int x, int[] vals)
    {
        for (int val : vals)
        {
            if (val == x)
                return true;
        }
        return false;
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

