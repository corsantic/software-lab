package util;

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
}


