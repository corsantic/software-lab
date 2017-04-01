package util;

import java.util.Arrays;
import java.util.List;

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
}
