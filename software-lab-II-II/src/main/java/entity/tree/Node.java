package entity.tree;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private int threshold;
    private String name;

    private Node root;
    private List<Node> left;
    private List<Node> right;

    public Node(int threshold, String name)
    {
        this.threshold = threshold;
        this.name = name;
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
    }

    public Node addLeftChild(Node node)
    {
        left.add(node);
        return node;
    }

    public Node addRightChild(Node node)
    {
        right.add(node);
        return node;
    }
}
