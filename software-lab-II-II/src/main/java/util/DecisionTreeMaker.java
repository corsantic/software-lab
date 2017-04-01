package util;

import static util.Commons.log2;
import static util.Constants.ATTRIBUTES;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import entity.Patient;
import entity.tree.Node;

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
        Node root = new Node(0, "root");
        new DecisionTreeMaker().test(root, patientList, "root");
        System.out.println(root);
    }


    private int[] getValue(String attr)
    {
        switch (attr)
        {
            case Constants.AGE_OF_AT_TIME_OF_OPERATION:
                return new int[]{50, 60, 70};

            case Constants.POSITIVE_AXILLARY_NODES_COUNT:
                return new int[]{62, 63, 64};

            case Constants.YEAR_OF_OPERATION:
                return new int[]{5, 10, 19};
            default:
                return new int[]{};
        }
    }

    private void test(Node root, List<Patient> patients, String direction)
    {
        String maxGain = getMaxGain(patients);

        int thresholdWithMaxGain = findThresholdWithMaxGain(patients, maxGain, getValue(maxGain));

        if (maxGain.equals(Constants.SURVIVAL_STATUS))
        {
            root.addLeftChild(new Node(countAttributeValue(patients, maxGain, 1), "1"));
            root.addRightChild(new Node(countAttributeValue(patients, maxGain, 2), "2"));
            return;
        }

        if (thresholdWithMaxGain == -1 || maxGain.equals(""))
            return;


        Node node = new Node(thresholdWithMaxGain, maxGain);

        switch (direction)
        {
            case "r":
                root.addRightChild(node);
                break;
            case "l":
                root.addLeftChild(node);
                break;
            case "root":
                root = node;
                break;
        }

        root.addLeftChild(new Node(thresholdWithMaxGain, maxGain));

        List<Patient> hig = getAttributeValueHigh(patients, maxGain, thresholdWithMaxGain);
        if (hig.size() > 0)
        {
            test(node, hig, "l");
        }


        List<Patient> lower = getAttributeValueBelow(patients, maxGain, thresholdWithMaxGain);
        if (lower.size() > 0)
        {
            test(node, lower, "r");
        }
    }


    private static int findThresholdWithMaxGain(List<Patient> patients, String attrName, int... values)
    {
        int maxValue = -1;
        double maxGain = -1;
        for (int value : values)
        {
            double gain = findGain(patients, attrName, value);
            //            System.out.println(" founded gain : " + gain);
            if (gain > maxGain)
            {
                maxValue = value;
                maxGain = gain;
            }
        }

        return maxValue;
    }

    private static double findGain(List<Patient> patients, String attrName, int value)
    {
        List<Integer> is = getValueList(patients, attrName); //todo

        double sum = 0;

        double allPossibilities = is.size();
        long l = countAttributeValueBelow(patients, attrName, value);

        List<Patient> subset = patients.stream()
                .filter(p -> Integer.valueOf(p.getAttributeValue(attrName)) < value)
                .collect(Collectors.toList());
        sum += l / allPossibilities * entropy(subset);

        // -----------


        l = countAttributeValueHigh(patients, attrName, value);
        subset = patients.stream()
                .filter(p -> Integer.valueOf(p.getAttributeValue(attrName)) >= value)
                .collect(Collectors.toList());
        sum += l / allPossibilities * entropy(subset);


        return entropy(patients) - sum;
    }

    private static double entropy(List<Patient> patients)
    {
        return Arrays.stream(new int[]{1, 2})
                .mapToDouble((x) ->
                {
                    long l = countAttributeValue(patients, Constants.SURVIVAL_STATUS, x);
                    if(l == 0) return 0;
                    double allPos = patients.size();
                    return -l / allPos * log2(l / allPos);
                }).sum();
    }

    private static double gain(List<Patient> patients, String attributeName)
    {
        List<Integer> valueList = getValueList(patients, attributeName);
        if (valueList == null || valueList.size() <= 0)
            return -1;

        double allPossibilities = valueList.size();
        double sum = valueList.stream().mapToDouble(val ->
        {
            List<Patient> subset = getPatientsThatValue(patients, attributeName, val);

            double result = subset.size() / allPossibilities * entropy(subset);

            return result;
        }).sum();

        double gain =  entropy(patients) - sum;

        System.out.println(attributeName + " - gain: " + gain);
        System.out.println(attributeName + " - entropy:  " + entropy(patients));
        System.out.println();
        return gain;
    }


    /**
     * bu deger kac kez kullanilmis
     */
    private static int countAttributeValue(List<Patient> patients, String name, int val)
    {
        return (int) patients.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .filter(value -> value.equals(val))
                .count();
    }

    private static long countAttributeValueBelow(List<Patient> patients, String name, int val)
    {
        return patients.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .filter(value -> value < val)
                .count();
    }

    private static long countAttributeValueHigh(List<Patient> patients, String name, int val)
    {
        return patients.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .filter(value -> value >= val)
                .count();
    }

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



    private static List<Integer> getValueList(List<Patient> patients, String name) // m
    {
        return patients.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .distinct().collect(Collectors.toList());
    }


    private String getMaxGain(List<Patient> patients)
    {
        String max = "";
        double maxGain = 0;
        for (String attributeName : ATTRIBUTES)
        {
            double gain = gain(patients, attributeName);
            if (gain > maxGain)
            {
                maxGain = gain;
                max = attributeName;
            }
        }

        return max;
    }
}
