package com.epam.spring.homework2.beans;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class BeanE extends Bean {

    public BeanE() {
    }

    public BeanE(String name, int value) {
        super(name, value);
    }

    @PostConstruct
    public void beanEInit() {
        System.out.println("PostConstruct: " + this.getClass().getSimpleName());
    }

    @PreDestroy
    public void beanEDestroy() {
        System.out.println("PreDestroy: " + this.getClass().getSimpleName());
    }
}
