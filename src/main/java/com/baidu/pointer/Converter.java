package com.baidu.pointer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2015/4/9
 * Time: 16:08
 */
public class Converter {

    public void doConvert(List<Long> point) {
        List<Long> newList = new ArrayList<Long>();
        newList.add(1L);
        point = newList;
        System.out.println("convert" + point);
    }

    public void convertMap(Map<String, Long> mapPoint) {
        Map<String, Long> newMap = new HashMap<String, Long>();
        newMap.put("a", 1L);
//        mapPoint = newMap;
        mapPoint.put("b", 2L);
        System.out.println("convert" + mapPoint);
    }

    public void convertInt(Integer point) {
        Integer newInt = new Integer(2);
//        point = newInt;
        point = point + 1;
        System.out.println("convert" + point);
    }
}
