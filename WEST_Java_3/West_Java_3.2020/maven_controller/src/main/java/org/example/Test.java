package org.example;

import java.util.Scanner;

/**
 * @author CXQ
 * @date 2022/1/13
 */
public class Test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ConCloMySql conCloMySql = new ConCloMySql();
        conCloMySql.connect();


        while (true) {

            int type;
            while (true) {
                System.out.println(".请输入序号（1.城市查询 2.天气查询）：");
                type = input.nextInt();
                if (type == 1 || type == 2) {
                    break;
                } else {
                    System.out.println("..输出错误 请重新输入！");
                }
            }
            System.out.println(".请输入城市：");
            String city = input.next();
            new GetInfo(type, city).init();
            if (type == 1) {
                System.out.println(".." + city + " 信息如下：");
            } else if (type == 2) {
                System.out.println(".." + city + " 近三天天气状况如下：");
            }
            new Read().read(type, city);

            System.out.println(".还要继续查询吗？(true or false)");
            boolean flag = input.nextBoolean();
            if (!flag) {
                break;
            }
        }



        conCloMySql.close();
    }
}
