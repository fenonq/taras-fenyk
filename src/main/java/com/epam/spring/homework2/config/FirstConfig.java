package com.epam.spring.homework2.config;

import com.epam.spring.homework2.beans.BeanB;
import com.epam.spring.homework2.beans.BeanC;
import com.epam.spring.homework2.beans.BeanD;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;


@Configuration
@Import(SecondConfig.class)
@PropertySource("classpath:application.properties")
public class FirstConfig {

    @Bean(initMethod = "myInitMethod",
            destroyMethod = "myDestroyMethod")
    @DependsOn("beanD")
    public BeanB beanB(@Value("${beanB.name}") final String name,
                       @Value("${beanB.value}") final int value) {
        return new BeanB(name, value);
    }

    @Bean(initMethod = "myInitMethod",
            destroyMethod = "myDestroyMethod")
    @DependsOn("beanB")
    public BeanC beanC(@Value("${beanC.name}") final String name,
                       @Value("${beanC.value}") final int value) {
        return new BeanC(name, value);
    }

    @Bean(initMethod = "myInitMethod",
            destroyMethod = "myDestroyMethod")
    public BeanD beanD(@Value("${beanD.name}") final String name,
                       @Value("${beanD.value}") final int value) {
        return new BeanD(name, value);
    }

}
