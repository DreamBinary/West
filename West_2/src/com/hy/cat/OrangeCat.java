package com.hy.cat;

/**
 * @author CXQ
 * @date 2021/11/12
 */

public class OrangeCat extends Cat {
    Boolean isFat;

    public OrangeCat() {
        this.price = 200;
    }


    public OrangeCat(String name, int age,Sex sex, Boolean isFat) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.isFat = isFat;
        this.price = 200;
    }

    @Override
    public String toString() {
        return "OrangeCat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
//                ", price=" + price +
                ", isFat=" + isFat +
                '}';
    }
}
