package util;

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

    void test()
    {
        long s = countAttributeValueCount(AttributeName.SURVIVAL_STATUS,  "2");
        System.out.println(s);
        System.out.println("entropy:"+entropy(AttributeName.AGE_OF_AT_TIME_OF_OPERATION));

    }


    private static double entropy(AttributeName s)
    {
        //entropy =lgn
      double sum=0;
   sum=Commons.log2(countUniqueAttributeValueCount(s));


        return sum;
    }




    private static long gain(int s, int a)
    {
        long sum = 0;


//        return entropy(s) - sum;
        return s;
    }


    /**
     * bu deger kac kez kullanilmis
     */
    private long countAttributeValueCount(AttributeName name, String val)
    {
        return patientList.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> patient.getAttributeValue(name))
                .filter(value -> value.equals(val))
                .count();
    }

    private static long countUniqueAttributeValueCount(AttributeName name) // m: attribute icin kac farkli deger var
    {
        return countUniqueValueList(name).size();
    }

    private static List<Integer> countUniqueValueList(AttributeName name) // m
    {
        return patientList.stream()
                .filter(patient -> patient.getAttributes().containsKey(name))
                .map(patient -> Integer.valueOf(patient.getAttributeValue(name)))
                .distinct().collect(Collectors.toList());
    }
}
