package com.baidu.dubbo;


import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;

import com.baidu.nuomi.crm.bdsbms.agent.BdsBmsAgent;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2015/1/19
 * Time: 19:20
 */
public class TestDubbo {

    public static void main(String[] args) {
        TestDubbo test = new TestDubbo();
        test.testAPI();
    }

    public void testAPI() {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("testDubbo");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress("10.94.34.33:8787");

        // 引用远程服务
        ReferenceConfig<BdsBmsAgent> reference = new ReferenceConfig<BdsBmsAgent>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(BdsBmsAgent.class);

        // 和本地bean一样使用xxxService
        BdsBmsAgent bdsBmsAgent = reference.get(); // 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
        boolean isOpen = bdsBmsAgent.isOpenBdsBms(1L);
        System.out.println("isOpen:" + isOpen);
    }

}
