package util;

import java.util.ArrayList;
import java.util.List;

import entity.Maze;

public interface Constants
{

    List<int[]> input55 = new ArrayList<int[]>()
    {{
        add(new int[]{5});
        add(new int[]{6});
        add(new int[]{3});
        add(new int[]{2, 8});
        add(new int[]{9});
        add(new int[]{0, 10});
        add(new int[]{1, 7});
        add(new int[]{6, 8});
        add(new int[]{3, 7, 9, 13});
        add(new int[]{4, 8});
        add(new int[]{5, 11});
        add(new int[]{10, 12, 16});
        add(new int[]{11, 13});
        add(new int[]{8, 12, 18});
        add(new int[]{19});
        add(new int[]{16, 20});
        add(new int[]{11, 15, 21});
        add(new int[]{18});
        add(new int[]{13, 17, 19});
        add(new int[]{14, 18, 24});
        add(new int[]{15});
        add(new int[]{16});
        add(new int[]{23});
        add(new int[]{22, 24});
        add(new int[]{19, 23});

    }};
    List<int[]> input33 = new ArrayList<int[]>()
    {{
        add(new int[]{3});
        add(new int[]{2, 4});
        add(new int[]{1, 5});
        add(new int[]{0, 4, 6});
        add(new int[]{1, 3});
        add(new int[]{2, 8});
        add(new int[]{3, 7});
        add(new int[]{6});
        add(new int[]{5});
    }};

    List<int[]> input22 = new ArrayList<int[]>()
    {{
        add(new int[]{1, 2});
        add(new int[]{0, 3});
        add(new int[]{0});
        add(new int[]{1});
    }};


    Maze INPUT_55 = new Maze(25, 3000, 2, 22, input55);
    Maze INPUT_33 = new Maze(9, 3000, 1, 7, input33);
    Maze INPUT_22 = new Maze(4, 3000, 0, 3, input22);

}
