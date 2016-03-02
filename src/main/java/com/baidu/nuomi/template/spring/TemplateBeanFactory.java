package com.baidu.nuomi.template.spring;

import com.baidu.nuomi.template.ServiceEntity;
import com.baidu.nuomi.template.TemplateEntity;
import com.baidu.nuomi.template.TemplateFunction;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

/**
 * Bean工厂，创建在模板中定义的服务实例，填充业务参数和扩展字段
 * 定时刷新，如发现模板定义中的服务有变化，则刷新spring上下文中的实例.
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

    /**
     * 根据应用使用的不同applicationContext，获取BeanFactory
     * @param applicationContext 应用使用的applicationContext
     */
    private void setSpringFactory(ApplicationContext applicationContext) {

        if (applicationContext instanceof AbstractRefreshableApplicationContext) {
            // suit both XmlWebApplicationContext and ClassPathXmlApplicationContext
            AbstractRefreshableApplicationContext springContext = (AbstractRefreshableApplicationContext) applicationContext;
            if (!(springContext.getBeanFactory() instanceof DefaultListableBeanFactory)) {
                LOGGER.error("No suitable bean factory! The current factory class is {}",
                        springContext.getBeanFactory().getClass());
            }
            springFactory = (DefaultListableBeanFactory) springContext.getBeanFactory();
        } else if (applicationContext instanceof GenericApplicationContext) {
            // suit GenericApplicationContext
            GenericApplicationContext springContext = (GenericApplicationContext) applicationContext;
            springFactory = springContext.getDefaultListableBeanFactory();
        } else {
            LOGGER.error("No suitable application context! The current context class is {}",
                    applicationContext.getClass());
        }
    }

    /**
     * 将模板中定义的service，填充业务参数和扩展字段，添加到BeanFactory的definition中
     */
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

    /**
     * 根据service信息，创建BeanDefinition
     * @param templateId 模板ID
     * @param serviceEntity service信息
     */
    private void registerBeanDefinition(String templateId, ServiceEntity serviceEntity) {

        try {
            AbstractBeanDefinition beanDefinition =
                    (AbstractBeanDefinition) springFactory.getBeanDefinition(serviceEntity.getImplName());

            AbstractBeanDefinition newDefinition = beanDefinition.cloneBeanDefinition();

            springFactory.registerBeanDefinition(
                    generateTemplateBeanName(templateId, serviceEntity.getServiceName()), newDefinition);
            LOGGER.info("Register bean definition successfully. beanName:{}, implName:{}",
                    generateTemplateBeanName(templateId, serviceEntity.getServiceName()), serviceEntity.getImplName());
        } catch (NoSuchBeanDefinitionException ex) {
            LOGGER.info("No bean definition in spring factory. implName:{}", serviceEntity.getImplName());
        } catch (BeanDefinitionStoreException ex) {
            LOGGER.info("Register bean definition wrong. beanName:{}, implName:{}",
                    generateTemplateBeanName(templateId, serviceEntity.getServiceName()), serviceEntity.getImplName());
        }
    }

    /**
     * 生成模板service实例名称
     * @param templateId 模板ID
     * @param serviceName service名称
     * @return
     */
    public static final String generateTemplateBeanName(String templateId, String serviceName) {
        StringBuilder builder = new StringBuilder(serviceName);
        builder.append("_");
        builder.append(templateId);
        return builder.toString();
    }
}
