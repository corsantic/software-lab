package util;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Commons
{
    //logaritm
    public static double logb(double a, double b)
    {
        return Math.log(a) / Math.log(b);
    }

    public static double log2(double a)
    {
        return logb(a, 2);
    }

    public static boolean isIn(String val, String... arr)
    {
        for (String s : arr)
        {
            if (s.equals(val))
                return true;
        }

        return false;
    }

    public interface Constants
    {
        Font UI_FONT = new Font("Tahoma", Font.BOLD, 20);

        String AGE_OF_AT_TIME_OF_OPERATION = "ageOfPatient"; // Age of patient at time of operation (numerical)
        String POSITIVE_AXILLARY_NODES_COUNT = "posAxNodesCount";
        String YEAR_OF_OPERATION = "yearOfOperation";
        String SURVIVAL_STATUS = "survivalStatus";

        java.util.List<String> ATTRIBUTES = Arrays.asList(AGE_OF_AT_TIME_OF_OPERATION,
                YEAR_OF_OPERATION,
                POSITIVE_AXILLARY_NODES_COUNT,
                SURVIVAL_STATUS);

        Map<String, int[]> SAMPLE_VALUES = new HashMap<String, int[]>()
        {{
            put(Constants.AGE_OF_AT_TIME_OF_OPERATION, new int[]{50, 60, 70});
            put(Constants.YEAR_OF_OPERATION, new int[]{62, 63, 64});
            put(Constants.POSITIVE_AXILLARY_NODES_COUNT, new int[]{5, 10, 19});
        }};
    }
}


