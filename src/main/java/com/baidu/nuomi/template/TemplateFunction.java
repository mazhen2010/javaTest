package com.baidu.nuomi.template;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2016/2/29
 * Time: 16:46
 */
public class TemplateFunction {

    public static List<TemplateEntity> getAllTemplateEntity() {

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setServiceName("demoService");
        serviceEntity.setImplName("demoServiceImpl");

        TemplateEntity templateEntity = new TemplateEntity();
        templateEntity.setTemplateId("ktv");
        List<ServiceEntity> serviceList = Lists.newArrayList();
        serviceList.add(serviceEntity);
        templateEntity.setServiceList(serviceList);

        List<TemplateEntity> templateEntityList = Lists.newArrayList();
        templateEntityList.add(templateEntity);
        return templateEntityList;
    }
}
