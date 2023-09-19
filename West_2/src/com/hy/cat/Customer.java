package com.hy.cat;

import java.time.LocalDate;

/**
 * @author CXQ
 * @date 2021/11/12
 */

public class Customer {
    String name;
    int ruaNum;
    LocalDate timeGet;

    public Customer() {

    }

    public Customer(String name, int ruaNum, LocalDate timeGet) {
        this.name = name;
        this.ruaNum = ruaNum;
        this.timeGet = timeGet;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", ruaNum=" + ruaNum +
                ", timeGet=" + timeGet +
                '}';
    }
}
