package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import entity.Patient;
import entity.Patient.SurvivalStatus;

/**
 *  file operations is here
 *  - write or reading
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
        patient.setAgeOfAtTimeOfOperation(Integer.valueOf(attrs[0]));
        patient.setYearOfOperation(Integer.valueOf(attrs[1]));
        patient.setPositiveAxillaryNodesCount(Integer.valueOf(attrs[2]));
        patient.setSurvivalStatus(survivalStatusFromVal(Integer.valueOf(attrs[3])));
        return patient;
    }


    private SurvivalStatus survivalStatusFromVal(int val)
    {
        return Arrays.stream(SurvivalStatus.values())
                .filter(s -> s.getVal() == val)
                .findFirst().get();
    }
}
