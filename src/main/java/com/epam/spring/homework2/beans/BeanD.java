package com.epam.spring.homework2.beans;

import org.springframework.stereotype.Component;

@Component
public class BeanD extends Bean {

    public BeanD() {
    }

    public BeanD(String name, int value) {
        super(name, value);
    }

    public void myInitMethod() {
        System.out.println("myInitMethod: " + this.getClass().getSimpleName());
    }

    public void myDestroyMethod() {
        System.out.println("myDestroyMethod: " + this.getClass().getSimpleName());
    }
}
