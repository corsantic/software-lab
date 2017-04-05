import java.awt.*;

import javax.swing.*;

import entity.tree.Node;
import util.DecisionTreeMaker;

/**
 * Agacı cizdirmek için ornek
 */

public class JPanelTest
{
    public static void main(String[] args)
    {
        JFrame jFrame = new JFrame();

        jFrame.setSize(800, 800);
        jFrame.setVisible(true);

        new JPanelTest().drawTree(jFrame);
    }

    public void drawTree(JFrame jFrame)
    {
        Graphics g = jFrame.getGraphics();

        Node node = DecisionTreeMaker.buildTree();

        g.drawOval(100, 100, 50, 50);


    }
}