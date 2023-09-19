package org.example.service;

import org.example.dao.ChangeMySql;

import java.util.Scanner;

/**
 * @author CXQ
 * @date 2021/12/24
 */
public class Change {
    static Scanner input = new Scanner(System.in);

    public static void change() {
        System.out.println(".请输入修改的类型（order or goods）、编号、要修改的属性、修改成的数据：");
        String type = input.next();
        String number = input.next();
        String changeItem = input.next();
        String changeData = input.next();
        new ChangeMySql().change(type,number,changeItem,changeData);
    }
}
