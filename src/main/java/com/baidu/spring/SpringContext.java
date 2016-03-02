package com.baidu.spring;

import com.baidu.nuomi.template.spring.demo.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2016/3/2
 * Time: 10:05
 */
public class SpringContext {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        if (context instanceof GenericApplicationContext) {
            System.out.println("context is instanceof GenericApplicationContext");
        }
        DemoService demoService = context.getBean("demoService_ktv", DemoService.class);
        demoService.doDemo();
        System.out.println(demoService.getClass());
    }
}
