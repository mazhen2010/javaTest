package com.baidu.dubbo;


import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.service.GenericService;

import com.baidu.nuomi.crm.bdsbms.agent.BdsBmsAgent;

import java.util.HashMap;
import java.util.Map;

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
        test.testGeneric();
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

    public void testGeneric() {
        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("testDubbo");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress("10.94.34.33:8787");

        // 引用远程服务
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>(); // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface("com.niux.acl.service.UserService"); // 弱类型接口名
        reference.setGeneric(true); // 声明为泛化接口

        GenericService genericService = reference.get(); // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Map result = (Map) genericService.$invoke("getUserByName", new String[] {"java.lang.String"}, new Object[] {"mazhen01@baidu.com"});
        System.out.println("result=" + result);

        // 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
//        Map<String, Object> person = new HashMap<String, Object>();
//        person.put("name", "xxx");
//        person.put("password", "yyy");
//        Object result = genericService.$invoke("findPerson", new String[]{"com.xxx.Person"}, new Object[]{person}); // 如果返回POJO将自动转成Map
    }
}
