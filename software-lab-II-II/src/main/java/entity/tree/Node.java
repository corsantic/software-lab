package entity.tree;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    public void setRoot(Node root)
    {
        this.root = root;
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

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
