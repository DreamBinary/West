package org.example.service;

import org.example.dao.DeleteMySql;
import javax.swing.*;

/**
 * @author CXQ
 * @date 2021/12/24
 */
public class Delete {

    public static void delete() {

        int typeNum = JOptionPane.showOptionDialog(null,
                "请选择删除的类型 : ","删除类型",JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,null,new String[] {"goods", "order"},"goods");
        String num = JOptionPane.showInputDialog("请输入删除信息的编号 ：");
        String type;
        if (typeNum == 0) {
            type = "goods";
        } else {
            type = "order";
        }
        new DeleteMySql().delete(type,num);
    }
}
