package com.epam.spring.homework2.bpp;

import com.epam.spring.homework2.beans.Bean;
import com.epam.spring.homework2.validation.BeanValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    private final BeanValidator beanValidator;

    public MyBeanPostProcessor(BeanValidator beanValidator) {
        this.beanValidator = beanValidator;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof Bean) {
            beanValidator.validate((Bean) bean);
        }

        return bean;
    }
}
