package net.optimalsolutionshub.csvfileconsumer.model;

public class LogFile {

    public static void main(String[] args) {
        String value = "Fada N''gourma";
        int index = value.indexOf("'");
        value = LogFile.builValidValue(value,index);
    }

    private static String builValidValue(String value, int index) {
        try {
            String startOfTheValue = "";
            String endOfTheValue = "";
            if (index == 0) {
                endOfTheValue = value;
                value = "'" + endOfTheValue;
                if (value.substring(index + 2)
                        .contains("'")) {
                    value = builValidValue(value, index + 2);
                }
            } else if (index == value.length() - 1) {
                startOfTheValue = value;
                value = startOfTheValue + "'";
            } else {
                index = value.indexOf("'");
                startOfTheValue = value.substring(0, index);
                endOfTheValue = value.substring(index);
                value = startOfTheValue + "'" + endOfTheValue;
                if (value.substring(index + 2)
                        .contains("'")) {
                    value = builValidValue(value, index + 2);
                }
            }
        }catch (StackOverflowError e) {
            System.out.println(value);
        }
        return value;
    }
}
