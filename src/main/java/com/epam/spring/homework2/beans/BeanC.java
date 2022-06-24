package com.epam.spring.homework2.beans;

import org.springframework.stereotype.Component;

@Component
public class BeanC extends Bean {

    public BeanC() {
    }

    public BeanC(String name, int value) {
        super(name, value);
    }

    public void myInitMethod() {
        System.out.println("myInitMethod: " + this.getClass().getSimpleName());
    }

    public void myDestroyMethod() {
        System.out.println("myDestroyMethod: " + this.getClass().getSimpleName());
    }
}
