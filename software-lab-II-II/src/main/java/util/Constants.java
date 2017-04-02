package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Constants
{
    String AGE_OF_AT_TIME_OF_OPERATION = "ageOfAtTimeOfOperation";
    String POSITIVE_AXILLARY_NODES_COUNT = "positiveAxillaryNodesCount";
    String YEAR_OF_OPERATION = "yearOfOperation";
    String SURVIVAL_STATUS = "survivalStatus";

    List<String> ATTRIBUTES = Arrays.asList(AGE_OF_AT_TIME_OF_OPERATION,
            POSITIVE_AXILLARY_NODES_COUNT,
            YEAR_OF_OPERATION,
            SURVIVAL_STATUS);

    Map<String, int[]> SAMPLE_VALUES = new HashMap<String, int[]>()
    {{
        put(Constants.AGE_OF_AT_TIME_OF_OPERATION, new int[]{50, 60, 70});
        put(Constants.POSITIVE_AXILLARY_NODES_COUNT, new int[]{62, 63, 64});
        put(Constants.YEAR_OF_OPERATION, new int[]{5, 10, 19});
    }};
}
