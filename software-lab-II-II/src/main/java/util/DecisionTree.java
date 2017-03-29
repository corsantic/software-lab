package util;

import static util.Commons.log2;

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
        Integer valueWithMaxGain = returnMaxGainValue(AttributeName.AGE_OF_AT_TIME_OF_OPERATION);
        double aa = entropy(AttributeName.AGE_OF_AT_TIME_OF_OPERATION);
        System.out.println(aa);

    }

    private int returnMaxGainValue(AttributeName attributeName)
    {
        List<Integer> values = uniqueValueList(attributeName);
        return values.stream().reduce((x, y) ->
        {
            int gainX = gain(attributeName, x);
            int gainY = gain(attributeName, y);
            return gainX > gainY ? x : y;
        }).get();
    }


    private static double entropy(AttributeName s)
    {
        List<Integer> is = uniqueValueList(s);

        return is.stream().mapToDouble(val ->
        {
            double allPossibilities = is.size();
            long l = countAttributeValue(s, val);
            double sum = - l / allPossibilities * log2(l / allPossibilities);
            return sum;
        }).sum();
    }


    private static int gain(AttributeName s, int a)
    {
        long sum = 0;


        //        return entropy(s) - sum;
        return 0;
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

    private static List<Integer> uniqueValueList(AttributeName name) // m
    {
        return patientList.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .distinct().collect(Collectors.toList());
    }
}
