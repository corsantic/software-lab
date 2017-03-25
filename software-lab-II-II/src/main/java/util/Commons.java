package util;

/**
 * Created by Furkan AYDIN on 25.3.2017.
 */
public class Commons
{


    public static int[] toIntArray(String[] sArray)
    {
        int[] array = new int[sArray.length];
        for (int i = 0; i < sArray.length; i++)
        {
            array[i] = Integer.parseInt(sArray[i]);
        }
        return array;
    }
}

