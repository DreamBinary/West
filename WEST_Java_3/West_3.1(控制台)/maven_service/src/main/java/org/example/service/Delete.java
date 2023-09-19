package org.example.service;

import org.example.dao.DeleteMySql;

import java.util.Scanner;

/**
 * @author CXQ
 * @date 2021/12/24
 */
public class Delete {
    static Scanner input = new Scanner(System.in);

    public static void delete() {
        System.out.println(".请输入要删除的类型（order or goods）和编号：");
        String type = input.next();
        String number = input.next();
        new DeleteMySql().delete(type,number);
    }
}
