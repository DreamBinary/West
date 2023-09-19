package org.example.dao;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author CXQ
 * @date 2021/12/21
 */
public class ChangeMySql {

    public void change(String type, String number, String changeItem, String changeData) {
        String str = null;
        if ("order".equals(type)) {
            str = "订单编号";
        } else if ("goods".equals(type)) {
            str = "商品编号";
        }
        String sql = "UPDATE `" + type + "`SET `" + changeItem + "` = '" + changeData + "' WHERE `" + str + "` = '" + number + "';";
        try {
            PreparedStatement preStmt = ConCloMySql.conn.prepareStatement(sql);
            preStmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"修改成功","SUCCESS",JOptionPane.INFORMATION_MESSAGE);
            ConCloMySql.conn.commit();
            preStmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            try {
                ConCloMySql.conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
