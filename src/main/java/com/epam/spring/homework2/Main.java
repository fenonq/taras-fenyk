package com.epam.spring.homework2;

import com.epam.spring.homework2.config.FirstConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(FirstConfig.class);

        Arrays.stream(context.getBeanDefinitionNames())
                .forEach(beanName -> System.out.println(context.getBean(beanName)));

        context.close();
    }
}
