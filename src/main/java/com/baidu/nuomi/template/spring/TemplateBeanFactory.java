package com.baidu.nuomi.template.spring;

import com.baidu.nuomi.template.ServiceEntity;
import com.baidu.nuomi.template.TemplateEntity;
import com.baidu.nuomi.template.TemplateFunction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2016/3/1
 * Time: 16:46
 */
public class TemplateBeanFactory implements ApplicationContextAware {

    private DefaultListableBeanFactory springFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateBeanFactory.class);

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setSpringFactory(applicationContext);
        loadTemplateBeanDefinitions();
    }

    private void setSpringFactory(ApplicationContext applicationContext) {

        if (applicationContext instanceof AbstractRefreshableApplicationContext) {
            // suit both XmlWebApplicationContext and ClassPathXmlApplicationContext
            AbstractRefreshableApplicationContext springContext = (AbstractRefreshableApplicationContext) applicationContext;
            if (!(springContext.getBeanFactory() instanceof DefaultListableBeanFactory)) {
                LOGGER.error("");
            }
            springFactory = (DefaultListableBeanFactory) springContext.getBeanFactory();
        } else if (applicationContext instanceof GenericApplicationContext) {
            // suit GenericApplicationContext
            GenericApplicationContext springContext = (GenericApplicationContext) applicationContext;
            springFactory = springContext.getDefaultListableBeanFactory();
        } else {
            LOGGER.error("");
        }
    }

    private void loadTemplateBeanDefinitions() {
        List<TemplateEntity> templateEntityList = TemplateFunction.getAllTemplateEntity();
        if (CollectionUtils.isEmpty(templateEntityList)) {
            LOGGER.warn("");
            return;
        }
        for (TemplateEntity templateEntity : templateEntityList) {
            if (templateEntity == null || CollectionUtils.isEmpty(templateEntity.getServiceList())) {
                continue;
            }
            String templateId = templateEntity.getTemplateId();
            for (ServiceEntity serviceEntity : templateEntity.getServiceList()) {
                registerBeanDefinition(templateId, serviceEntity);
            }
        }
    }

    private void registerBeanDefinition(String templateId, ServiceEntity serviceEntity) {
        AbstractBeanDefinition beanDefinition =
                (AbstractBeanDefinition) springFactory.getBeanDefinition(serviceEntity.getImplName());

        AbstractBeanDefinition newDefinition = beanDefinition.cloneBeanDefinition();

        springFactory.registerBeanDefinition(
                generateTemplateBeanName(templateId, serviceEntity.getServiceName()), newDefinition);
    }

    public static final String generateTemplateBeanName(String templateId, String serviceName) {
        StringBuilder builder = new StringBuilder(serviceName);
        builder.append("_");
        builder.append(templateId);
        return builder.toString();
    }
}
