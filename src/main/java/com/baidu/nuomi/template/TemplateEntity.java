package com.baidu.nuomi.template;

import com.baidu.nuomi.template.spring.annotation.TemplateService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2016/3/1
 * Time: 17:58
 */
public class TemplateEntity {

    private String templateId;

    private List<ServiceEntity> serviceList;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<ServiceEntity> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceEntity> serviceList) {
        this.serviceList = serviceList;
    }
}
