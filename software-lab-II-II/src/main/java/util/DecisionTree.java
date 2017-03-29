package util;

import static util.Commons.log2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import entity.AttributeName;
import entity.Patient;

/**
 * c4.5 and decision tree stuffs is here
 * <p>
 * Entropy : E(S)    =  sum:i:1->n  - Pr(Ci) * log2(Pr(Ci))
 * Gain    : G(S, A) =  E(S) - sum(i:1->m  Pr(Ai) * E(SAi))
 *
 * @E(S) – information entropy of S
 * @G(S,A) – gain of S after a split on attribute A
 * @n – nr of classes in S //-- all patients
 * @Pr(Ci) – frequency of class Ci in S
 * @m – nr of values of attribute A in S
 * @Pr(Ai) – frequency of cases that have Ai value in S
 * @E(SAi) – subset of S with items that have Ai value
 */
public class DecisionTree
{
    private static FileHelper fileHelper = new FileHelper();
    private static List<Patient> patientList = fileHelper.readAllPatients();

    public static void main(String[] args)
    {
        new DecisionTree().test();
    }

    private void test()
    {
        AttributeName maxGainAttribute = getMaxGain();

        System.out.println(maxGainAttribute + "   >  ");
    }

    private AttributeName getMaxGain()
    {
        AttributeName max = AttributeName.SURVIVAL_STATUS;
        double maxGain = 0;
        for (AttributeName attributeName : AttributeName.values())
        {
            double gain = gain(attributeName);
            if (gain > maxGain)
            {
                maxGain = gain;
                max = attributeName;
            }
        }

        return max;
    }

    private static double entropy(List<Patient> patients)
    {
        return Arrays.stream(new int[]{1, 2})
                .mapToDouble((x) ->
                {
                    long l = countAttributeValue(AttributeName.SURVIVAL_STATUS, x);
                    double allPos = patients.size();
                    return -l / allPos * log2(l / allPos);
                }).sum();
    }


    private static double gain(AttributeName attributeName)
    {
        List<Integer> is = uniqueValueList(attributeName);

        double sum = is.stream().mapToDouble(val ->
        {
            double allPossibilities = is.size();
            long l = countAttributeValue(attributeName, val);

            List<Patient> subset = getPatientsThatValue(attributeName, val);

            double result = l / allPossibilities * entropy(subset);

            return result;
        }).sum();
        return entropy(patientList) - sum;
    }


    /**
     * bu deger kac kez kullanilmis
     */
    private static long countAttributeValue(AttributeName name, int val)
    {
        return patientList.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .filter(value -> value.equals(val))
                .count();
    }

    private static long countUniqueAttributeValue(AttributeName name) // m: attribute icin kac farkli deger var
    {
        return uniqueValueList(name).size();
    }

    private static List<Patient> getPatientsThatValue(AttributeName name, int value) // m
    {
        return patientList.stream()
                .filter(patient -> patient.getAttributeValue(name).equals(String.valueOf(value)))
                .collect(Collectors.toList());
    }

    private static List<Integer> uniqueValueList(AttributeName name) // m
    {
        return patientList.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .distinct().collect(Collectors.toList());
    }
}
