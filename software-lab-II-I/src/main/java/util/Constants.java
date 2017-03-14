package util;

import java.util.HashMap;
import java.util.Map;

import entity.Maze;

public interface Constants {


    Map<Integer, int[]> input55 = new HashMap<Integer, int[]>() {{
        put(0, new int[]{5});
        put(1, new int[]{6});
        put(2, new int[]{3});
        put(3, new int[]{2, 8});
        put(4, new int[]{9});
        put(5, new int[]{0, 10});
        put(6, new int[]{1, 7});
        put(7, new int[]{6, 8});
        put(8, new int[]{3, 7, 9, 13});
        put(9, new int[]{4, 8});
        put(10, new int[]{5, 11});
        put(11, new int[]{10, 12, 16});
        put(12, new int[]{11, 13});
        put(13, new int[]{8, 12, 18});
        put(14, new int[]{19});
        put(15, new int[]{16, 20});
        put(16, new int[]{11, 15, 21});
        put(17, new int[]{18});
        put(18, new int[]{13, 17, 19});
        put(19, new int[]{14, 18, 24});
        put(20, new int[]{15});
        put(21, new int[]{16});
        put(22, new int[]{23});
        put(23, new int[]{22, 24});
        put(24, new int[]{19, 23});

    }};
    static Map<Integer, int[]> input33 = new HashMap<Integer, int[]>() {{
        put(0, new int[]{3});
        put(1, new int[]{2, 4});
        put(2, new int[]{1, 5});
        put(3, new int[]{0, 4, 6});
        put(4, new int[]{1, 3});
        put(5, new int[]{2, 8});
        put(6, new int[]{3, 7});
        put(7, new int[]{6});
        put(8, new int[]{5});
    }};

    static Map<Integer, int[]> input22 = new HashMap<Integer, int[]>() {{
        put(0, new int[]{1, 2});
        put(1, new int[]{0, 3});
        put(2, new int[]{0});
        put(3, new int[]{1});
    }};


    Maze INPUT_55 = new Maze(25, 3000, 2, 22, input55);
    Maze INPUT_33 = new Maze(9, 3000, 1, 7, input33);
    Maze INPUT_22 = new Maze(4, 3000, 0, 3, input22);

}
