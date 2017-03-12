import entity.Lab;
import qlearning.QLearning;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Initializer {
    QLearning qLearning = new QLearning();

    public static void main(String[] args) {
        new Initializer().run(args);
    }

    void run(String[] args) {
//        Lab lab = buildLab();
        Lab lab = buildDummyLab();
        int[][] rMatrix = qLearning.getRMatrix(lab.getN(), lab.getEndPoint(), lab.getNeighbours());
        writeMatrix(rMatrix);

        System.out.println("-----------------------");
        System.out.println("------- Q Matrix ---------");
        double[][] qMatrix = qLearning.buildQMatrix(lab.getNeighbours(), lab.getIterationCount(), lab.getStartPoint(), lab.getEndPoint(), lab.getN());

        writeMatrix(qMatrix);

    }

    private Lab buildDummyLab() {

        Lab lab = new Lab(input33.keySet().size());

        lab.setNeighbours(input33);

        lab.setStartPoint(0);
        lab.setEndPoint(7);
        lab.setIterationCount(3000);

        return lab;
    }

    private Lab buildLab() {
        Scanner input = new Scanner(System.in);
        System.out.print("matrix size: ");
        int n = input.nextInt();
        Lab lab = new Lab(n);
        input.nextLine(); //tmp

        for (int i = 0; i < n * n; i++) {
            System.out.print(i + ". neighbours: ");
            String nb = input.nextLine();
            lab.getNeighbours().put(i, toIntArray(nb.split(",")));
        }

        System.out.print("start: ");
        lab.setStartPoint(input.nextInt());

        System.out.print("end: ");
        lab.setEndPoint(input.nextInt());

        System.out.print("iterationCount: ");
        lab.setIterationCount(input.nextInt());

        System.out.println("builded lab: " + lab.toString());
        return lab;
    }

    private void writeMatrix(int[][] r) {
        int n = r.length;
        for (int i = 0; i < n; i++)

        {
            System.out.println("");
            for (int j = 0; j < n; j++) {
                System.out.print(r[i][j] + " ");

            }
        }
    }

    private void writeMatrix(double[][] r) {
        int n = r.length;
        for (int i = 0; i < n; i++)

        {
            System.out.println("");
            for (int j = 0; j < n; j++) {
                System.out.print(r[i][j] + " ");

            }
        }
    }

    private int[] toIntArray(String[] sArray) {
        int[] array = new int[sArray.length];
        for (int i = 0; i < sArray.length; i++) {
            array[i] = Integer.parseInt(sArray[i]);
        }
        return array;
    }


    static Map<Integer, int[]> input55 = new HashMap<Integer, int[]>() {{
        put(0, new int[]{1, 3});
        put(1, new int[]{5});
        put(2, new int[]{6});
        put(3, new int[]{3});
        put(4, new int[]{2, 8});
        put(5, new int[]{9});
        put(6, new int[]{0, 10});
        put(7, new int[]{1, 7});
        put(8, new int[]{6, 8});
        put(9, new int[]{3, 7, 9, 13});
        put(10, new int[]{4, 8});
        put(11, new int[]{5, 11});
        put(12, new int[]{10, 12, 16});
        put(13, new int[]{11, 13});
        put(14, new int[]{8, 12, 18});
        put(15, new int[]{19});
        put(16, new int[]{16, 20});
        put(17, new int[]{11, 15, 21});
        put(18, new int[]{18});
        put(19, new int[]{13, 17, 19});
        put(20, new int[]{14, 18, 24});
        put(21, new int[]{15});
        put(22, new int[]{16});
        put(23, new int[]{23});
        put(24, new int[]{22, 24});
        put(25, new int[]{19, 23});
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


}

