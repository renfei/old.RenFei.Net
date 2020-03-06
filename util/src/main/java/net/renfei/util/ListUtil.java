package net.renfei.util;

import java.util.List;

/**
 * List工具类
 *
 * @author RenFei
 */
public class ListUtil {
    public static <E> E getOne(List<E> list) {
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}









