import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class QLearningInitializer
{



    public static void main(String[] args) throws IOException {


//        Scanner input=new Scanner(System.in);
//        System.out.print("beginning room: ");
//
//        String out=input.nextLine();
//        System.out.print("target room:");
//        String in=input.nextLine();
     //   new QLearningInitializer().run(args);

        int n;


        File file = new File("document/software-lab-II-1/inputs/input22.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();

        while (line != null) {
            System.out.println(line);
            line = reader.readLine();


    }
    n=line.length();
        int i=0,j=0;
        int[][] R=new int[n][n];






    }

    void run(String[] args)
    {
        System.out.println("......");
    }



}

