package com.baidu.nuomi.template.spring.demo;

import com.baidu.nuomi.template.spring.annotation.TemplateService;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2016/3/1
 * Time: 19:00
 */

@TemplateService(serviceName = "demoService", implName = "demoServiceImpl")
public class DemoServiceImpl implements DemoService {

    public void doDemo() {
        System.out.println("doDemo");
    }
}
