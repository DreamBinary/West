package org.example.service;

import org.example.dao.AddMySql;

import java.util.Scanner;

/**
 * @author CXQ
 * @date 2021/12/24
 */
public class Add {
    static Scanner input = new Scanner(System.in);

    public static void add() {
        System.out.println(".请输入要添加的数据\n[商品信息格式：goods 商品编号 商品名 价格]" +
                "\n[订单信息格式：order 订单编号 商品编号 商品名 价格 下单时间(YY-MM-DD_HH:mm:SS)]\n ：");
        String type = input.next();
        if ("order".equals(type)) {
            String orderNumber = input.next();
            String goodNumber = input.next();
            String name = input.next();
            String price = input.next();
            String time = input.next();
            new AddMySql("order",orderNumber,goodNumber,name,price,time).add();
        } else if ("goods".equals(type)) {
            String goodNumber = input.next();
            String name = input.next();
            String price = input.next();
            new AddMySql("goods",goodNumber,name,price).add();
        }
    }
}
