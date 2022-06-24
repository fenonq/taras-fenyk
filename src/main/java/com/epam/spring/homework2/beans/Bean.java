package com.epam.spring.homework2.beans;

public abstract class Bean implements MyValidator {

    private String name;
    private int value;

    public Bean() {
    }

    public Bean(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void validate() {
        if (this.name == null) {
            this.name = "Not null";
        }

        if (this.value <= 0) {
            this.value = Math.abs(this.value) + 1;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
