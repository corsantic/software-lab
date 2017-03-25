import static util.Commons.toIntArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Initializer
{
    private static final String FILE_NAME ="inputs/haberman.txt";
    public static void main(String[] args) throws FileNotFoundException
    {
        new Initializer().run();
    }

    void run() throws FileNotFoundException
    {

       readAttributesFromFile(FILE_NAME);


    }


    private List<int[]> readAttributesFromFile(String fileName) throws FileNotFoundException

    {
        List<int[]> attributes = new ArrayList<>();
        File file = new File(getFilePath(fileName));
        Scanner read = new Scanner(file);

        while (read.hasNextLine())
        {
            String[] attri = read.nextLine().split(",");
            attributes.add(toIntArray(attri));

        }
        //faaa




        return attributes;


    }
    private String getFilePath(String filename)
    {
        ClassLoader loader = getClass().getClassLoader();
        return loader.getResource(filename).getFile();
    }
}

