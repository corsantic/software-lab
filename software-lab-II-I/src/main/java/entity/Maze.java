package entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

public class Maze {
    int n;
    int iterationCount;
    int startPoint;
    int endPoint;

    Map<Integer, int[]> neighbours;

    public Maze(int n) {
        this.n = n;
        this.neighbours = new HashMap<>();
    }

    public Maze(int n, int iterationCount, int startPoint, int endPoint, Map<Integer, int[]> neighbours) {
        this.n = n;
        this.iterationCount = iterationCount;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.neighbours = neighbours;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getIterationCount() {
        return iterationCount;
    }

    public void setIterationCount(int iterationCount) {
        this.iterationCount = iterationCount;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }

    public Map<Integer, int[]> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Map<Integer, int[]> neighbours) {
        this.neighbours = neighbours;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
