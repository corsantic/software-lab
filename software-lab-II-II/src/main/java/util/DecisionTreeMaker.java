package util;

import static util.Commons.isIn;
import static util.Commons.log2;
import static util.Constants.ATTRIBUTES;
import static util.Constants.SAMPLE_VALUES;
import static util.Constants.SURVIVAL_STATUS;

import java.util.ArrayList;
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
    private static Node tree = new Node("-");

    public static void main(String[] args)
    {
        buildTree();
        BinaryTreeUtils.printNode(tree);

//        new treeGUI(tree);
    }

    public static Node buildTree()
    {
        new DecisionTreeMaker().run(tree, patientList, "root", Arrays.asList(SURVIVAL_STATUS));
        return tree;
    }

    // * Entropy : E(S)    =  sum:i:1->n  - Pr(Ci) * log2(Pr(Ci))
    private static double entropy(List<Patient> patients, String attrName)
    {
        return getAllValues(patients, attrName)
                .stream().distinct().mapToDouble((value) -> // distinct
                {
                    double count = getPatientsThatValue(patients, attrName, value).size();
                    double pr = count / patients.size();

                    return count == 0 ? 0 : -pr * log2(pr);
                }).sum();
    }

    private static double informationGain(List<Patient> patients, String attrName)
    {
        double sum = getAllValues(patients, attrName)
                .stream().distinct().mapToDouble((value) -> // distinct
                {
                    List<Patient> subValues = getPatientsThatValue(patients, attrName, value);

                    double pr = subValues.size() / Double.valueOf(patients.size());

                    return pr * entropy(subValues, SURVIVAL_STATUS);
                }).sum();


        double gain = entropy(patients, SURVIVAL_STATUS) - sum;
        //        System.out.format("%s attr gain: %s \n", attrName, gain);
        return gain;
    }


    private static double entropy(List<Patient> patients, String attrName, int thresold)
    {
        double sum = 0;
        List<Patient> subset = getAttributeValueBelow(patients, attrName, thresold);
        double v = subset.size() / Double.valueOf(patients.size());
        sum += -v * entropy(subset, SURVIVAL_STATUS); // ?

        subset = getAttributeValueHigh(patients, attrName, thresold);
        v = subset.size() / Double.valueOf(patients.size());
        sum += -v * entropy(subset, SURVIVAL_STATUS); // ?

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

    private static List<Patient> getPatientsThatValue(List<Patient> patients, String name, int value) // m
    {
        return patients.stream()
                .filter(patient -> patient.getAttributeValue(name).equals(String.valueOf(value)))
                .collect(Collectors.toList());
    }

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


    // -------------------------------------------------------------------- //
    // -------------------------------------------------------------------- //
    // -------------------------------------------------------------------- //
    // -------------------------------------------------------------------- //

    private void run(Node root, List<Patient> patients, String direction, List<String> exclude)
    {
        String attrWithMaxEntropy = findAttributeWithMaxInformationGain(patients, exclude.toArray(new String[0]));
        if (attrWithMaxEntropy.equals(""))
        {
            System.out.println(patients.size());
            add(root, patients);
            return;
        }

        int thresholdWithMaxGain = findThresholdWithMaxGain(patients, attrWithMaxEntropy, SAMPLE_VALUES.get(attrWithMaxEntropy));

        if (thresholdWithMaxGain == -1)
        {
            System.out.println(patients.size());
            add(root, patients);
            return;
        }


        Node node = addNode(root, direction, attrWithMaxEntropy, thresholdWithMaxGain);

        List<String> subExclude = new ArrayList<>(exclude);
        subExclude.add(attrWithMaxEntropy);


        List<Patient> rightSubSet = getAttributeValueHigh(patients, attrWithMaxEntropy, thresholdWithMaxGain);
        List<Patient> leftSubSet = getAttributeValueBelow(patients, attrWithMaxEntropy, thresholdWithMaxGain);
        if (isOk(rightSubSet, subExclude) && isOk(leftSubSet, subExclude))
        {
            run(node, leftSubSet, "left", subExclude);
            run(node, rightSubSet, "right", subExclude);
        } else {
            add(node, patients);
        }


    }

    private boolean isOk(List<Patient> patients, List<String> excludes)
    {
        String subattrWithMaxEntropy = findAttributeWithMaxInformationGain(patients, excludes.toArray(new String[0]));
        if (subattrWithMaxEntropy.equals(""))
            return false;
        int subthresholdWithMaxGain = findThresholdWithMaxGain(patients, subattrWithMaxEntropy, SAMPLE_VALUES.get(subattrWithMaxEntropy));
        if (subthresholdWithMaxGain == -1)
            return false;
        return true;
    }


    private void add(Node root, List<Patient> patients)
    {
        String name = "1(" + getPatientsThatValue(patients, SURVIVAL_STATUS, 1).size() + ")";
        root.left = new Node(name);


        name = "2(" + getPatientsThatValue(patients, SURVIVAL_STATUS, 2).size() + ")";
        root.right = new Node(name);

        System.out.println(" + " + patients.size() + " = " + root.left.data + "  + " + root.right.data);
    }

    private Node addNode(Node root, String direction, String attrWithMaxEntropy, int thresholdWithMaxGain)
    {
        Node newNode = new Node(attrWithMaxEntropy + "<" + thresholdWithMaxGain + ">");
        switch (direction)
        {
            case "root":
                root.data = newNode.data;
                return root;

            case "left":
                root.left = newNode;
                break;

            case "right":
                root.right = newNode;
                break;

        }

        //        System.out.println(direction + " : " + attrWithMaxEntropy + ": " + thresholdWithMaxGain);
        return newNode;
    }

    private static String findAttributeWithMaxInformationGain(List<Patient> patients, String... exclude)
    {
        //        System.out.println("-------------------");
        String max = "";
        double maxInfo = -5000d;
        for (String attributeName : ATTRIBUTES)
        {
            if (isIn(attributeName, exclude))
                continue;

            double informationGain = informationGain(patients, attributeName);
            //            System.out.format("entropy: %s, attr: %s \n", informationGain, attributeName);
            if (informationGain > maxInfo)
            {
                maxInfo = informationGain;
                max = attributeName;
            }
        }
        //        System.out.println("-------------------");
        return max;
    }


}
