package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Node;

/**
 * @souece: http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 */
public class BinaryTreeUtils
{
    public static void printNode(Node root)
    {

        int maxLevel = maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<Node> nodes, int level, int maxLevel)
    {
        if (nodes.isEmpty() || BinaryTreeUtils.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BinaryTreeUtils.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes)
        {
            if (node != null)
            {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            }
            else
            {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BinaryTreeUtils.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++)
        {
            for (int j = 0; j < nodes.size(); j++)
            {
                BinaryTreeUtils.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null)
                {
                    BinaryTreeUtils.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BinaryTreeUtils.printWhitespaces(1);

                BinaryTreeUtils.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BinaryTreeUtils.printWhitespaces(1);

                BinaryTreeUtils.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count)
    {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static int maxLevel(Node node)
    {
        if (node == null)
            return 0;

        return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    private static boolean isAllElementsNull(List<Node> list)
    {
        for (Node node : list)
        {
            if (node != null)
                return false;
        }

        return true;
    }

}
