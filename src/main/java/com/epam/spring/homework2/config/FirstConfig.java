package com.epam.spring.homework2.config;

import com.epam.spring.homework2.beans.BeanB;
import com.epam.spring.homework2.beans.BeanC;
import com.epam.spring.homework2.beans.BeanD;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource("classpath:application.properties")
@Import(SecondConfig.class)
public class FirstConfig {

    private final Environment environment;

    public FirstConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(initMethod = "myInitMethod",
            destroyMethod = "myDestroyMethod")
    @DependsOn("beanD")
    public BeanB beanB() {
        return new BeanB(environment.getProperty("beanB.name"),
                validateInteger(environment.getProperty("beanB.value")));
    }

    @Bean(initMethod = "myInitMethod",
            destroyMethod = "myDestroyMethod")
    @DependsOn("beanB")
    public BeanC beanC() {
        return new BeanC(environment.getProperty("beanC.name"),
                validateInteger(environment.getProperty("beanC.value")));
    }

    @Bean(initMethod = "myInitMethod",
            destroyMethod = "myDestroyMethod")
    public BeanD beanD() {
        return new BeanD(environment.getProperty("beanD.name"),
                validateInteger(environment.getProperty("beanD.value")));
    }

    private int validateInteger(String value) {
        return value == null ? 0 : Integer.parseInt(value);
    }
}
