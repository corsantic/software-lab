package tree;


import static util.Commons.Constants.ATTRIBUTES;
import static util.Commons.Constants.SAMPLE_VALUES;
import static util.Commons.Constants.SURVIVAL_STATUS;
import static util.Commons.isIn;
import static util.Commons.log2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import entity.Node;
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
    public Node buildTree(List<Patient> patients)
    {
        Node node = new Node("-");
        buildTree(node, patients, "root", Arrays.asList(SURVIVAL_STATUS));
        return node;
    }

    private double entropy(List<Patient> patients, String attrName)
    {
        return patients.stream()
                .map(patient -> Integer.valueOf(patient.getAttributeValue(attrName)))
                .distinct().mapToDouble((value) ->
                {
                    double count = getPatientsThatValue(patients, attrName, value).size();
                    double pr = count / patients.size();

                    return count == 0 ? 0 : -pr * log2(pr);
                }).sum();
    }

    private double informationGain(List<Patient> patients, String attrName)
    {
        double sum = patients.stream()
                .map(patient -> Integer.valueOf(patient.getAttributeValue(attrName)))
                .distinct().mapToDouble((value) ->
                {
                    List<Patient> subValues = getPatientsThatValue(patients, attrName, value);

                    double pr = subValues.size() / Double.valueOf(patients.size());
                    return pr * entropy(subValues, SURVIVAL_STATUS);
                }).sum();


        return entropy(patients, SURVIVAL_STATUS) - sum;
    }

    private double informationGain(List<Patient> patients, String attrName, int threshold)
    {
        List<Patient> subset = getAttributeValueBelow(patients, attrName, threshold);

        double pr = subset.size() / Double.valueOf(patients.size());
        double sum = -pr * entropy(subset, SURVIVAL_STATUS);

        subset = getAttributeValueHigh(patients, attrName, threshold);
        pr = subset.size() / Double.valueOf(patients.size());
        sum += -pr * entropy(subset, SURVIVAL_STATUS);
        return sum;
    }


    private int findThresholdWithMaxGain(List<Patient> patients, String attrName, int... values)
    {
        int maxValue = -1;
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<Result>> tasks = new ArrayList<>();

        for (int value : values)
        {
            Callable<Result> t = new Callable<Result>()
            {
                @Override
                public Result call() throws Exception
                {
                    double gain = informationGain(patients, attrName, value);
                    Result r = new Result();
                    r.gain = gain;
                    r.threshold = value;
                    return r;
                }
            };
            tasks.add(t);
        }

        try
        {
            Result reduce = executor.invokeAll(tasks) /** invokeAll: tum tasklarin tamamlanmasini bekliyor */
                    .stream()
                    .map(future ->
                    {
                        try
                        {
                            return future.get();
                        }
                        catch (Exception e)
                        {
                            throw new IllegalStateException(e);
                        }
                    })
                    .reduce((a, b) -> a.gain > b.gain ? a : b).get();
            return reduce.threshold;
        }
        catch (InterruptedException e)
        {


        }

        executor.shutdown();


        return maxValue;
    }

    private String findAttributeWithMaxInformationGain(List<Patient> patients, String... exclude)
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

    private void buildTree(Node root, List<Patient> patients, String direction, List<String> exclude)
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
            buildTree(node, leftSubSet, "left", subExclude);
            buildTree(node, rightSubSet, "right", subExclude);
        }
        else
        {
            add(node, patients);
        }


    }

    private boolean isOk(List<Patient> patients, List<String> excludes)
    {
        int size = getPatientsThatValue(patients, SURVIVAL_STATUS, 1).size();
        if (size == 0 || size == patients.size())
            return false;


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
        name += " 2(" + getPatientsThatValue(patients, SURVIVAL_STATUS, 2).size() + ")";
        root.left = new Node(name);
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

    class Result
    {
        public double gain;
        public int threshold;
    }


    private static List<Patient> getPatientsThatValue(List<Patient> patients, String attributeName, int value)
    {
        return patients.stream()
                .filter(patient -> patient.getAttributeValue(attributeName).equals(String.valueOf(value)))
                .collect(Collectors.toList());
    }

    private static List<Patient> getAttributeValueBelow(List<Patient> patients, String name, int threshold)
    {
        return patients.stream()
                .filter(patient -> Integer.valueOf(patient.getAttributeValue(name)) < threshold)
                .collect(Collectors.toList());
    }

    private static List<Patient> getAttributeValueHigh(List<Patient> patients, String name, int threshold)
    {
        return patients.stream()
                .filter(patient -> Integer.valueOf(patient.getAttributeValue(name)) >= threshold)
                .collect(Collectors.toList());
    }
}
