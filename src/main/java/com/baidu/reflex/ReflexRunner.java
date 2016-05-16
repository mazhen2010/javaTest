package com.baidu.reflex;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2016/2/24
 * Time: 17:46
 */
public class ReflexRunner {

    public void doReflex(Poi poi) {
        Field fields[] = poi.getClass().getDeclaredFields();//获得对象所有属性
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }

    public static void main(String[] args) {
        PoiKTV poi = new PoiKTV();
        poi.setId(1);
        poi.setName("name");
        poi.setBrand("brand");

        ReflexRunner reflexRunner = new ReflexRunner();
        reflexRunner.doReflex(poi);
    }
}
