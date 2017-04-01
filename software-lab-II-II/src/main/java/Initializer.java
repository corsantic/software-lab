import java.util.List;

import entity.Patient;
import util.FileHelper;

public class Initializer
{
    private FileHelper fileHelper = new FileHelper();

    public static void main(String[] args)
    {
        new Initializer().run();
    }

    private void run()
    {
        List<Patient> patients = fileHelper.readAllPatients();
        System.out.println("All patients");
        patients.forEach(System.out::println);
    }
}

