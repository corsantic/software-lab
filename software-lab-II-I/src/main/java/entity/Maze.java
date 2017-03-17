package entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Maze
{
    int n;
    int iterationCount;
    int startPoint;
    int endPoint;

    List<int[]> neighbours;

    public Maze()
    {

    }

    public Maze(int n)
    {
        this.n = n;
        this.neighbours = new ArrayList<>();
    }

    public Maze(int n, int iterationCount, int startPoint, int endPoint, List<int[]> neighbours)
    {
        this.n = n;
        this.iterationCount = iterationCount;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.neighbours = neighbours;
    }

    public int getN()
    {
        return n;
    }

    public void setN(int n)
    {
        this.n = n;
    }

    public int getIterationCount()
    {
        return iterationCount;
    }

    public void setIterationCount(int iterationCount)
    {
        this.iterationCount = iterationCount;
    }

    public int getStartPoint()
    {
        return startPoint;
    }

    public void setStartPoint(int startPoint)
    {
        this.startPoint = startPoint;
    }

    public int getEndPoint()
    {
        return endPoint;
    }

    public void setEndPoint(int endPoint)
    {
        this.endPoint = endPoint;
    }

    public List<int[]> getNeighbours()
    {
        return neighbours;
    }

    public void setNeighbours(List<int[]> neighbours)
    {
        this.neighbours = neighbours;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
