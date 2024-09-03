package io.dataSynthAI.dataSynthAI_backend.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class DataSynthUtils {

    public static boolean validateDataType(String value, String datatype) {
        try {
            switch (datatype) {
                case "integer":
                    Integer.parseInt(value);
                    break;
                case "string":
                    // Strings are always valid for the string datatype
                    break;
                // Add more cases as needed for other datatypes
                default:
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validateRange(String range, String datatype) {
        try {
            switch (datatype) {
                case "integer":
                    validateIntegerRange(range);
                    break;
                case "string":
                    // Strings are always valid for the string datatype
                    break;
                // Add more cases as needed for other datatypes
                default:
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateIntegerRange(String range) {
        if(StringUtils.contains(range, "-")){
            String first = range.substring(0,1);
            String last = range.substring(range.lastIndexOf("-") + 1);
            Integer.parseInt(first);
            Integer.parseInt(last);
        }
    }

    public static String getErrorMessage(String message){
        if(StringUtils.isNotBlank(message) && StringUtils.containsIgnoreCase(message, "$.Columns[")){
            String columnStr = message.substring(message.indexOf(".") + 1, message.lastIndexOf("."));
            String errMsg = message.substring(message.lastIndexOf(".") + 1);
            int colVal = Integer.parseInt(columnStr.substring(columnStr.indexOf("[") + 1, columnStr.lastIndexOf("]")));
            colVal = colVal + 1;
            return errMsg + " for column num " + colVal;
        }
        return message;
    }
}
