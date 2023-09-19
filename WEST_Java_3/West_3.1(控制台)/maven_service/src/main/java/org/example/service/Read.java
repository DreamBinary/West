package org.example.service;

import org.example.dao.ReadFyMysql;
import org.example.dao.ReadMySql;

import java.util.Scanner;

/**
 * @author CXQ
 * @date 2021/12/24
 */
public class Read {
    static Scanner input = new Scanner(System.in);

    public static void read(){
        System.out.println(".单项查询（1）还是分页查询（2）？[输入序号]");
        int a =input.nextInt();
        if (a == 1) {
            System.out.println(".请输入要查询的类型（order or goods）和编号 ：");
            String type = input.next();
            String number = input.next();
            new ReadMySql(type,number).read();
        } else if (a == 2) {
            System.out.println(".请输入要查询的类型（order or goods）和每页数据量：");
            String type = input.next();
            int onePage = input.nextInt();
            ReadFyMysql readFyMysql = new ReadFyMysql(type, onePage);
            int pageNum = readFyMysql.getPageNum();
            System.out.println(".一共 " + pageNum + " 页，你要查询哪一页：");
            int num = input.nextInt();
            readFyMysql.getPage(num);
        }
    }
}
