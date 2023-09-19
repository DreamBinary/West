package com.hy.cat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author CXQ
 * @date 2021/11/12
 */

public class Test {
    public static void main(String[] args) throws InsufficientBalanceException, CatNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("-----先搞个店吧！-----");
        System.out.println("请输入店名、余额：");
        MyCatCafe mcc = new MyCatCafe();
        mcc.name = input.next();
        mcc.balance = input.nextDouble();
        System.out.println("OK!你的店 " + mcc.name + " 余额为 " + mcc.balance + " 元");
//买猫
        System.out.println("-----还没有猫哟！-----");
        System.out.println("请输入要买猫的数量：");
        int n = input.nextInt();
        for (int i = 0; i < n; i++) {
//搞新猫
            Cat aCat = newCat(i);
            try {
                mcc.buyCat(aCat);
            } catch (InsufficientBalanceException e) {
                e.printStackTrace();
                System.out.println("---余额不足 仅有 " + mcc.balance + " 元" + " 无法购买---");
                if (aCat instanceof BlackCat && mcc.balance >= 200) {
                    System.out.println("可以买一只橘猫哦！买吗？(true or false)");
                    boolean buy = input.nextBoolean();
                    if (buy) {
                        i--;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        System.out.println("买猫后余额：" + mcc.balance + " 元");
        System.out.println("店里的猫咪：" + mcc.myCat);
//待客
        System.out.println("-----一切就绪 只缺顾客-----");
        System.out.println("请输入到店客人人数：");
        int m = input.nextInt();
        for (int i = 0; i < m; i++) {
            Customer cus = new Customer();
            System.out.println("请输入第 " + (i + 1) + " 位到店客人信息(姓名 rua次数 到店时间(yyyy-MM-dd)：");
            String name = input.next();
            int ruaNum = input.nextInt();
            String date = input.next();
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            cus.name = name;
            cus.ruaNum = ruaNum;
            cus.timeGet = localDate;
            mcc.treatCustomer(cus);
        }
        System.out.println("接待客人后余额：" + mcc.balance);
        System.out.println("-----该歇业啦！-----");
        System.out.println("请输入歇业日期：");
        String date0 = input.next();
        LocalDate date1 = LocalDate.parse(date0, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        mcc.close(date1);
    }

    public static Cat newCat(int i) {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入第 " + (i + 1) + " 只要买猫的类型：");
        String type = null;
        boolean judge1 = true;
        while (judge1) {
            type = input.next().toLowerCase(Locale.ROOT);
            if ("黑猫".equals(type) || "blackcat".equals(type) || "orangecat".equals(type) || "橘猫".equals(type)) {
                judge1 = false;
            } else {
                System.out.println("没有此类型猫 请重新输入");
            }
        }
        System.out.println("请输入要买猫的名字、年龄、性别：");
        String name = input.next();
        int age = input.nextInt();
        Sex sex = null;
        while (true){
            String sexIn = input.next();
            try {
                sex = Sex.valueOf(sexIn);
                break;
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("性别输入错误 请重新输入");
            }
        }
        if ("黑猫".equals(type) || "blackcat".equals(type)) {
            BlackCat bc = new BlackCat();
            bc.name = name;
            bc.age = age;
            bc.sex = sex;
            return bc;
        } else {
            OrangeCat oc = new OrangeCat();
            oc.name = name;
            oc.age = age;
            oc.sex = sex;
            System.out.println("橘猫要胖的吗：");
            boolean isFat = input.nextBoolean();
            oc.isFat = isFat;
            return oc;
        }
    }
}
