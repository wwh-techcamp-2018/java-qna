package codesquad.web;

public class ValidateUtils {

    public static boolean validateString(String input){
        return input!=null && !(input.equals(""));
    }

    public static boolean validateObject(Object input){
        return input != null;
    }
}
