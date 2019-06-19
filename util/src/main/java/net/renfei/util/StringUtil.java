package net.renfei.util;

public class StringUtil {
    public boolean isEmpty(String string) {
        return string == null ? true : (string.length() == 0 ? true : false);
    }

    public int convertInt(String str, int defaultValue) {
        int result = defaultValue;
        if (!isEmpty(str)) {
            try {
                result = Integer.valueOf(str);
            } catch (NumberFormatException nfe) {
                result = defaultValue;
            }
        }
        return result;
    }
}
