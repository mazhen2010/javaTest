package com.baidu.nuomi.template.spring.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: mazhen01
 * Date: 2016/3/1
 * Time: 17:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface TemplateService {

    String serviceName() default "";
    String implName() default "";

}
