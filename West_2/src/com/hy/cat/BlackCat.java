package com.hy.cat;

/**
 * @author CXQ
 * @date 2021/11/12
 */

public class BlackCat extends Cat {
    public BlackCat() {
        this.price = 350;

    }

    public BlackCat(String name, int age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.price = 350;
    }

    @Override
    public String toString() {
        return "BlackCat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
//                ", price=" + price +
                '}';
    }
}
