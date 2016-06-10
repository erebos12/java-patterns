package string;

public class StringUtils {

    public static boolean isStringNullOrEmpty (String string)
    {
        return (string == null || string.trim().isEmpty()) ? true : false;
    }

}
