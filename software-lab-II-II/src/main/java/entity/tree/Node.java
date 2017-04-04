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
    private String targetLabel;//anlık target almak içiin

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
        setTargetLabel(root.toString());
    }

    public Node addLeftChild(Node node)
    {
        left.add(node);
        setTargetLabel(node.toString());
        return node;
    }

    public Node addRightChild(Node node)
    {
        right.add(node);
        setTargetLabel(node.toString());
        return node;
    }
    public void setTargetLabel(String targetLabel)
    {
        this.targetLabel=targetLabel;
    }
     public String getTargetLabel()
    {
     return targetLabel;
    }



    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
