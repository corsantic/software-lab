package tree;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.Node;
import util.Commons.Constants;

/**
 * @source https://github.com/EslaMx7/AI-Tasks-JADE-Tests/blob/master/src/trees/tasks/treeGUI.java
 */
public class BinaryTreeUI extends JFrame
{
    public BinaryTreeUI(Node tree)
    {
        JPanel contentPanel = new JPanel();
        DrawTree drawer = new DrawTree(tree);

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.add(drawer);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 900);
        setContentPane(contentPanel);
        setVisible(true);
    }

    class DrawTree extends JPanel
    {
        private Node tree;

        private DrawTree(Node tree)
        {
            this.tree = tree;
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            drawTree(g, 0, getWidth(), 0, getHeight() / maxLevel(tree), tree);
        }

        private void drawTree(Graphics g, int startWidth, int endWidth, int startHeight, int level, Node node)
        {
            g.setFont(Constants.UI_FONT);
            FontMetrics fm = g.getFontMetrics();
            int dataWidth = fm.stringWidth(node.data);
            g.drawString(node.data, (startWidth + endWidth) / 2 - dataWidth / 2, startHeight + level / 2);

            if (node.left != null)
                drawTree(g, startWidth, (startWidth + endWidth) / 2, startHeight + level, level, node.left);

            if (node.right != null)
                drawTree(g, (startWidth + endWidth) / 2, endWidth, startHeight + level, level, node.right);
        }

        private int maxLevel(Node node)
        {
            if (node == null)
                return 0;

            return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
        }
    }
}