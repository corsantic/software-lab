package util;

import static util.Commons.Constants.ATTRIBUTES;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entity.Patient;

/**
 * file operations is here
 * - write or reading
 */

public class FileHelper
{
    private final static String ATTRIBUTE_SEPERATOR = ",";
    private final static String FILENAME = "haberman.txt";

    public List<Patient> readAllPatients()
    {
        List<Patient> patients = new ArrayList<>();

        try
        {
            String filePath = getClass().getClassLoader().getResource(FILENAME).getFile();
            File patientFile = new File(filePath);

            Scanner read = new Scanner(patientFile);

            while (read.hasNextLine())
            {
                String[] attrs = read.nextLine().split(ATTRIBUTE_SEPERATOR);

                patients.add(buildPatientFromAttributes(attrs));
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return patients;
    }

    private Patient buildPatientFromAttributes(String[] attrs)
    {
        Patient patient = new Patient();

        for (int i = 0; i < ATTRIBUTES.size(); i++)
        {
            patient.getAttributes().put(ATTRIBUTES.get(i), attrs[i]);
        }

        return patient;
    }
}
