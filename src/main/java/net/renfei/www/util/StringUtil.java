package net.renfei.www.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {
    public boolean isEmpty(String string) {
        return string == null ? true : (string.length() == 0 ? true : false);
    }
}
