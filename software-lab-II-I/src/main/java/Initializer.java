import static util.Commons.toIntArray;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import util.UIHelper;


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
    private UIHelper uiHelper = new UIHelper();

    public static void main(String[] args) throws Exception
    {
        new Initializer().run(args);
    }

    void run(String[] args) throws Exception
    {

        JFrame frame = uiHelper.createStandartJPanel();


        GridBagConstraints gridBagConstraints = createConstraints();

        JPanel vertexPanel = createMainPanel();


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


    private JPanel createMainPanel()
    {
        JPanel panel = new JPanel();

        JTextField start = uiHelper.createInput("st");
        JTextField end = uiHelper.createInput("end");
        JTextField itera = uiHelper.createInput("itera");


        JButton submit = uiHelper.createStandartButton("Solve");
        JButton exportFiles = uiHelper.createStandartButton("Export Files");
        submit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
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
            }
        });

        exportFiles.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt)
            {
                int s = Integer.valueOf(start.getText());
                int e = Integer.valueOf(end.getText());
                int i = Integer.valueOf(itera.getText());


                qLearningMazeSolver.exportFiles(createMaze(s, e, i));
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

        GridBagConstraints gridBagConstraints = createConstraints();
        JFrame frame = uiHelper.createStandartJPanel();
        JPanel vertexPanel = createVertexPanel(maze);


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
        //        maze.setN((int) Math.sqrt(neighbours.size()));

        maze.setStartPoint(start);
        maze.setEndPoint(end);
        maze.setIterationCount(iteration);
        return maze;
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


    private GridBagConstraints createConstraints()
    {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.fill = GridBagConstraints.ABOVE_BASELINE;
        return gridBagConstraints;
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

