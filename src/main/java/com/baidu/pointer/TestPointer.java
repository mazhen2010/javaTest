package com.baidu.pointer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2015/4/9
 * Time: 15:57
 */
public class TestPointer {

    public List<Long> point = new ArrayList<Long>();

    public Map<String, Long> mapPoint = new HashMap<String, Long>();

    public Integer intPoint = new Integer(1);

    public List<Long> getPoint() {
        return point;
    }

    public void setPoint(List<Long> point) {
        this.point = point;
    }

    public static void main(String[] args) {
        TestPointer pointer = new TestPointer();
        pointer.testList();
        pointer.testMap();
        pointer.testInt();
    }

    public void testList() {
        Converter converter = new Converter();
        converter.doConvert(point);
        System.out.println(point);
    }

    public void testMap() {
        Converter converter = new Converter();
        converter.convertMap(mapPoint);
        System.out.println(mapPoint);
    }


    public void testInt() {
        Converter converter = new Converter();
        converter.convertInt(intPoint);
        System.out.println(intPoint);
    }
}
