package com.kxj.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author xiangjin.kong
 * @date 2021/11/17 13:40
 * @desc 转换工具类
 */
public class ConvertUtil {

    private ConvertUtil() {

    }

    /**
     * List转Map
     *
     * @param list
     * @return
     */
    public static <K, V> Map<K, V> listToMap(List<V> list, Function<V, K> function) {
        if (list == null || list.isEmpty()) {
            return Maps.newHashMap();
        }
        Map<K, V> map = new HashMap<>(list.size());
        for (V v : list) {
            K key = function.apply(v);
            if (key != null) {
                map.put(key, v);
            }
        }
        return map;
    }

    /**
     * List转List
     */
    public static <V, K> List<K> listToList(List<V> list, Function<V, K> function) {
        if (list == null || list.isEmpty()) {
            return Lists.newArrayList();
        }
        List<K> newList = new ArrayList<>(list.size());
        for (V v : list) {
            K k = function.apply(v);
            if (k != null) {
                newList.add(k);
            }
        }
        return newList;
    }

    /**
     * Map转List
     * @param map
     * @param function
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V, T> List<T> mapToList(Map<K, V> map, Function<Map.Entry<K, V>, T> function) {
        if (map == null || map.isEmpty()) {
            return Lists.newArrayList();
        }
        List<T> list = new ArrayList<>(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            T v = function.apply(entry);
            if (v != null) {
                list.add(v);
            }
        }
        return list;
    }


}
