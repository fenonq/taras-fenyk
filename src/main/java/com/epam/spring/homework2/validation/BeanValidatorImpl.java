package com.epam.spring.homework2.validation;

import com.epam.spring.homework2.beans.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanValidatorImpl implements BeanValidator {

    @Override
    public void validate(Bean bean) {
        if (bean.getName() == null) {
            bean.setName("Not null");
        }

        if (bean.getValue() <= 0) {
            bean.setValue(Math.abs(bean.getValue()) + 1);
        }
    }
}
