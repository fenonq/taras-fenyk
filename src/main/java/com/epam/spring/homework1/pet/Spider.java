package com.epam.spring.homework1.pet;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(30)
public class Spider implements Animal {
}
