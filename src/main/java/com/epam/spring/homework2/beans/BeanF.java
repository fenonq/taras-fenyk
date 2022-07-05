package com.epam.spring.homework2.beans;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class BeanF extends Bean {

    public BeanF() {
    }

    public BeanF(String name, int value) {
        super(name, value);
    }
}
