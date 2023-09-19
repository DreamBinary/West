package com.hy.mailbox;

import java.util.Scanner;

/**
 * @author CXQ
 * @date 2021/11/13
 */
public class MailBox {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入一个邮箱号码：");
        String mailbox = input.next();
        System.out.println("邮箱符合格式吗？" + isMailbox(mailbox));
    }

    public static boolean isMailbox(String mailbox){
        return mailbox.matches("^[\\w]+@[a-z0-9]+\\.[a-zA-Z]+$");
    }




}
