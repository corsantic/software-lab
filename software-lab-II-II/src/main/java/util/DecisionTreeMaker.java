package util;

import static util.Commons.log2;
import static util.Constants.ATTRIBUTES;
import static util.Constants.SAMPLE_VALUES;

import java.util.List;
import java.util.stream.Collectors;

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
public class DecisionTreeMaker
{
    private static FileHelper fileHelper = new FileHelper();
    private static List<Patient> patientList = fileHelper.readAllPatients();

    public static void main(String[] args)
    {
        new DecisionTreeMaker().run(patientList);
    }

    private void run(List<Patient> patients)
    {
        String attrWithMaxEntropy = findAttributeWithMaxEntropy(patients);
        int thresholdWithMaxGain = findThresholdWithMaxGain(patients, attrWithMaxEntropy, SAMPLE_VALUES.get(attrWithMaxEntropy));

        System.out.println(attrWithMaxEntropy + ": " + thresholdWithMaxGain);


        List<Patient> attributeValueBelow = getAttributeValueBelow(patients, attrWithMaxEntropy, thresholdWithMaxGain);
        String attrWithMaxEntropyBelow = findAttributeWithMaxEntropy(attributeValueBelow, attrWithMaxEntropy);
        int thresholdWithMaxGainBelow = findThresholdWithMaxGain(attributeValueBelow, attrWithMaxEntropy, SAMPLE_VALUES.get(attrWithMaxEntropy));
        System.out.println("left " + attrWithMaxEntropyBelow + ": " + thresholdWithMaxGainBelow);


        List<Patient> attributeValueHigh = getAttributeValueHigh(patients, attrWithMaxEntropy, thresholdWithMaxGain);
        String attrWithMaxEntropyHigh = findAttributeWithMaxEntropy(attributeValueHigh, attrWithMaxEntropy);
        int thresholdWithMaxGainHigh = findThresholdWithMaxGain(attributeValueHigh, attrWithMaxEntropy, SAMPLE_VALUES.get(attrWithMaxEntropy));
        System.out.println("right " + attrWithMaxEntropyHigh + ": " + thresholdWithMaxGainHigh);


    }


    private String findAttributeWithMaxEntropy(List<Patient> patients, String... exclude)
    {
        System.out.println("-------------------");
        String max = "";
        double maxEntropy = -5000d;
        for (String attributeName : ATTRIBUTES)
        {
            if(isIn(attributeName, exclude))
                continue;

            double entropy = entropy(patients, attributeName);
//            System.out.format("entropy: %s, attr: %s \n", entropy, attributeName);
            if (entropy > maxEntropy)
            {
                maxEntropy = entropy;
                max = attributeName;
            }
        }
        System.out.println("-------------------");
        return max;
    }

    private boolean isIn(String val, String... arr)
    {
        for (String s : arr)
        {
            if (s.equals(val))
                return true;
        }

        return false;
    }

    // * Entropy : E(S)    =  sum:i:1->n  - Pr(Ci) * log2(Pr(Ci))
    private static double entropy(List<Patient> patients, String attrName)
    {
        return getAllValues(patients, attrName)
                .stream().distinct().mapToDouble((value) -> // distinct
                {
                    double count = getPatients(patients, attrName, value).size();
                    double pr = count / patients.size();

                    return count == 0 ? 0 : -pr * log2(pr);
                }).sum();
    }


    private static double entropy(List<Patient> patients, String attrName, int thresold)
    {
        double sum = 0;
        List<Patient> subset = getAttributeValueBelow(patients, attrName, thresold);
        double v = subset.size() / Double.valueOf(patients.size());
        sum += -v * log2(v); // ?

        subset = getAttributeValueHigh(patients, attrName, thresold);
        v = subset.size() / Double.valueOf(patients.size());
        sum += -v * log2(v); // ?

//        System.out.format("attr: %s, thresold: %s, entropy: %s \n", attrName, thresold, sum);
        return sum;
    }

    private static List<Integer> getAllValues(List<Patient> patients, String name) // m
    {
        return patients.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .collect(Collectors.toList());
    }

    private static List<Integer> getPatients(List<Patient> patients, String name, int expectedValue)
    {
        return patients.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .filter(value -> value.equals(expectedValue)).collect(Collectors.toList());
    }


    // ----------------------------------
    // ----------------------------------
    // ----------------------------------
    // ----------------------------------


    private static int findThresholdWithMaxGain(List<Patient> patients, String attrName, int... values)
    {
        int maxValue = -1;
        double maxGain = -1;
        for (int value : values)
        {
            double gain = entropy(patients, attrName, value);
            if (gain > maxGain)
            {
                maxValue = value;
                maxGain = gain;
            }
        }

        return maxValue;
    }

    private static double findGainForThreshold(List<Patient> patients, String attrName, int value)
    {
        double sum = 0;
        double allPossibilities = patients.size();

        List<Patient> subset = getAttributeValueBelow(patients, attrName, value);
        //        sum += subset.size() / allPossibilities * entropy(subset);

        // -----------

        subset = getAttributeValueHigh(patients, attrName, value);
        //        sum += subset.size() / allPossibilities * entropy(subset);


        //        return entropy(patients) - sum;
        return 0;
    }


    private static double gain(List<Patient> patients, String attributeName)
    {
        List<Integer> values = getAllValues(patients, attributeName);
        if (values == null || values.size() <= 0)
            return -1;

        //        double sum = values.stream().mapToDouble(val ->
        //        {
        //            List<Patient> subset = getPatientsThatValue(patients, attributeName, val);

        //            double pr = subset.size() / Double.valueOf(patients.size());

        //            double result = pr * entropy(subset);

        //            return result;
        //        }).sum();

        //        System.out.println("entropy: " + entropy(patients));
        //        double gain = entropy(patients) - sum;
        return 0;
    }


    /**
     * bu deger kac kez kullanilmis
     */


    private static List<Patient> getAttributeValueBelow(List<Patient> patients, String name, int val)
    {
        return patients.stream()
                .filter(patient -> patient.getAttributes().containsKey(name) &&
                        Integer.valueOf(patient.getAttributeValue(name)) < val)
                .collect(Collectors.toList());
    }

    private static List<Patient> getAttributeValueHigh(List<Patient> patients, String name, int val)
    {
        return patients.stream()
                .filter(patient -> patient.getAttributes().containsKey(name) &&
                        Integer.valueOf(patient.getAttributeValue(name)) >= val)
                .collect(Collectors.toList());
    }


    private static List<Patient> getPatientsThatValue(List<Patient> patients, String name, int value) // m
    {
        return patients.stream()
                .filter(patient -> patient.getAttributeValue(name).equals(String.valueOf(value)))
                .collect(Collectors.toList());
    }


}
