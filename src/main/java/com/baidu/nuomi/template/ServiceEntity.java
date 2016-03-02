package com.baidu.nuomi.template;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2016/3/1
 * Time: 17:57
 */
public class ServiceEntity {

    private String serviceName;

    private String implName;

    private Map<String, String> bizParamMap;

    private List<String> extendFields;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getImplName() {
        return implName;
    }

    public void setImplName(String implName) {
        this.implName = implName;
    }

    public Map<String, String> getBizParamMap() {
        return bizParamMap;
    }

    public void setBizParamMap(Map<String, String> bizParamMap) {
        this.bizParamMap = bizParamMap;
    }

    public List<String> getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(List<String> extendFields) {
        this.extendFields = extendFields;
    }
}
