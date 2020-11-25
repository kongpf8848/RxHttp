package com.github.kongpf8848.rxhttp.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtil {


    public static void removeNullEntry(Map map){
        removeNullKey(map);
        removeNullValue(map);
    }

    public static void removeNullKey(Map map){
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object key = (Object) iterator.next();
            if(key==null){
                iterator.remove();
            }
        }
    }

    public static void removeNullValue(Map map){
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object key = (Object) iterator.next();
            Object value =(Object)map.get(key);
            if(value==null){
                iterator.remove();
            }
        }
    }




}
