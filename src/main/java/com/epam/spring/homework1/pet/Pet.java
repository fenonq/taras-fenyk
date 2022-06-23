package com.epam.spring.homework1.pet;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Pet implements Animal {

    List<Animal> animals;

    public Pet(List<Animal> animals) {
        this.animals = animals;
    }

    public void printPets() {
        animals.forEach(animal -> System.out.println(animal.getClass().getSimpleName()));
    }
}
