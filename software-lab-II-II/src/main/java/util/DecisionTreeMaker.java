package util;

import static util.Commons.log2;
import static util.Constants.ATTRIBUTES;
import static util.Constants.SAMPLE_VALUES;

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

    private void test(Node root, List<Patient> patients, String direction)
    {
        String attributeWithMaxGain = findAttributeWithMaxGain(patients);

        if (Constants.SURVIVAL_STATUS.equals(attributeWithMaxGain))
        {
            root.addLeftChild(new Node(countAttributeValue(patients, attributeWithMaxGain, 1), "1"));
            root.addRightChild(new Node(countAttributeValue(patients, attributeWithMaxGain, 2), "2"));
            return;
        }

        int thresholdWithMaxGain = findThresholdWithMaxGain(patients, attributeWithMaxGain, SAMPLE_VALUES.get(attributeWithMaxGain));

        if (thresholdWithMaxGain == -1 || attributeWithMaxGain.equals(""))
            return;


        Node node = new Node(thresholdWithMaxGain, attributeWithMaxGain);

        switch (direction)
        {
            case "r":
                root.addRightChild(node);
                break;
            case "l":
                root.addLeftChild(node);
                break;
            case "root":
                root.setRoot(node);
                break;
        }


        List<Patient> hig = getAttributeValueHigh(patients, attributeWithMaxGain, thresholdWithMaxGain);
        if (hig.size() > 0)
        {
            test(node, hig, "l");
        }


        List<Patient> lower = getAttributeValueBelow(patients, attributeWithMaxGain, thresholdWithMaxGain);
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
        List<Integer> values = getValues(patients, attrName); //todo
        double sum = 0;
        double allPossibilities = values.size();

        List<Patient> subset = getAttributeValueBelow(patients, attrName, value);
        sum += subset.size() / allPossibilities * entropy(subset);

        // -----------

        subset = getAttributeValueHigh(patients, attrName, value);
        sum += subset.size() / allPossibilities * entropy(subset);


        return entropy(patients) - sum;
    }

    // * Entropy : E(S)    =  sum:i:1->n  - Pr(Ci) * log2(Pr(Ci))
    private static double entropy(List<Patient> patients)
    {
        return getValues(patients, Constants.SURVIVAL_STATUS)
                .stream().distinct().mapToDouble((value) ->
                {
                    long count = countAttributeValue(patients, Constants.SURVIVAL_STATUS, value);
                    double pr = count / Double.valueOf(patients.size());

                    return count == 0 ? 0 : -pr * log2(pr);
                }).sum();
    }

    private static double gain(List<Patient> patients, String attributeName)
    {
        List<Integer> values = getValues(patients, attributeName);
        if (values == null || values.size() <= 0)
            return -1;

        double sum = values.stream().mapToDouble(val ->
        {
            List<Patient> subset = getPatientsThatValue(patients, attributeName, val);

            double result = subset.size() / Double.valueOf(patients.size()) * entropy(subset);

            return result;
        }).sum();

        double gain = entropy(patients) - sum;

        System.out.println(attributeName + " - sum: " + sum);
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


    private static List<Integer> getValues(List<Patient> patients, String name) // m
    {
        return patients.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .distinct().collect(Collectors.toList());
    }


    private String findAttributeWithMaxGain(List<Patient> patients)
    {
        String max = "";
        double maxGain = -5000d;
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
