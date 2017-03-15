package mazesolver;


import java.util.List;

import entity.Maze;

public interface MazeSolver
{
    List<Integer> findPath(Maze maze);
}
