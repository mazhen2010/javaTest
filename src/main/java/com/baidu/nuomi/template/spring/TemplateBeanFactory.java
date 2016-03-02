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
 * Bean������������ģ���ж���ķ���ʵ�������ҵ���������չ�ֶ�
 * ��ʱˢ�£��緢��ģ�嶨���еķ����б仯����ˢ��spring�������е�ʵ��.
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
     * ����Ӧ��ʹ�õĲ�ͬapplicationContext����ȡBeanFactory
     * @param applicationContext Ӧ��ʹ�õ�applicationContext
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
     * ��ģ���ж����service�����ҵ���������չ�ֶΣ���ӵ�BeanFactory��definition��
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
     * ����service��Ϣ������BeanDefinition
     * @param templateId ģ��ID
     * @param serviceEntity service��Ϣ
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
     * ����ģ��serviceʵ������
     * @param templateId ģ��ID
     * @param serviceName service����
     * @return
     */
    public static final String generateTemplateBeanName(String templateId, String serviceName) {
        StringBuilder builder = new StringBuilder(serviceName);
        builder.append("_");
        builder.append(templateId);
        return builder.toString();
    }
}
