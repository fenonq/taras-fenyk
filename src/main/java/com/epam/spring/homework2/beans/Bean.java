package com.epam.spring.homework2.beans;

public abstract class Bean {

    private String name;
    private int value;

    public Bean() {
    }

    public Bean(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void myInitMethod() {
        System.out.println("myInitMethod: " + this.getClass().getSimpleName());
    }

    public void myDestroyMethod() {
        System.out.println("myDestroyMethod: " + this.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
