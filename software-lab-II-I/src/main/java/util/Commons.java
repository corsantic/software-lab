package util;

import java.util.Random;

/**
 * Created by stuff on 3/12/2017.
 */
public class Commons {

    public static void writeMatrix(int[][] r) {
        int n = r.length;
        for (int i = 0; i < n; i++)

        {
            System.out.println("");
            for (int j = 0; j < n; j++) {
                System.out.print(r[i][j] + " ");

            }
        }
    }

    public static int rand(int min, int max) {
        int rand = new Random().nextInt((max - min) + 1) + min;
        return rand;
    }


    public static void writeMatrix(double[][] r) {
        int n = r.length;
        for (int i = 0; i < n; i++)

        {
            System.out.println("");
            for (int j = 0; j < n; j++) {
                System.out.print(r[i][j] + " ");

            }
        }
    }

    public static int[] toIntArray(String[] sArray) {
        int[] array = new int[sArray.length];
        for (int i = 0; i < sArray.length; i++) {
            array[i] = Integer.parseInt(sArray[i]);
        }
        return array;
    }
}
