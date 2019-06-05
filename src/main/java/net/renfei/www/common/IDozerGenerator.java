package net.renfei.www.common;

import java.util.List;
import java.util.Set;

public interface IDozerGenerator {
    /**
     * @Description: 单个对象的深度复制及类型转换，vo/domain , po
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> T convert(S s, Class<T> clz);

    /**
     * @Description: 深度复制结果集(ResultSet为自定义的分页结果集)
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     */
//    <T, S> ResultSet<T> convert(ResultSet<S> s, Class<T> clz);

    /**
     * @Description: list深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> List<T> convert(List<S> s, Class<T> clz);

    /**
     * @Description: set深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> Set<T> convert(Set<S> s, Class<T> clz);

    /**
     * @Description: 数组深度复制
     * @param s 数据对象
     * @param clz 复制目标类型
     * @return
     */
    <T, S> T[] convert(S[] s, Class<T> clz);
}
