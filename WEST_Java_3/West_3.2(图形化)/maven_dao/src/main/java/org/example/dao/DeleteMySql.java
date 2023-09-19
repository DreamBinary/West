package org.example.dao;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author CXQ
 * @date 2021/12/21
 */
public class DeleteMySql {

    public void delete(String type, String number) {
        String sql;
        String str = null;
        if ("order".equals(type)) {
            str = "订单编号";
        } else if ("goods".equals(type)) {
            str = "商品编号";
        }
        sql = "DELETE FROM `" + type + "` WHERE  `" + str + "` = '" + number + "';";
        try {
            PreparedStatement preStmt = ConCloMySql.conn.prepareStatement(sql);
            if (preStmt.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null,"删除成功","SUCCESS",JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,"不存在此数据","WARNING",JOptionPane.WARNING_MESSAGE);
            }
            ConCloMySql.conn.commit();
            preStmt.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            ArrayList<String> ordNum = delRead(number);
            JOptionPane.showMessageDialog(null,"有订单中含有此商品暂不可删除！！！（需先删除订单信息）\n 订单编号如下：\n" + ordNum,"删除异常",JOptionPane.WARNING_MESSAGE);
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

    public ArrayList<String> delRead(String goodNumber) {
        ArrayList<String> ordNums = new ArrayList<>();
        try {
            Statement stmt = ConCloMySql.conn.createStatement();
            String sql = "SELECT * FROM `order` WHERE `商品信息` = '" + goodNumber + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String orderNumber = rs.getString("订单编号");
                ordNums.add(orderNumber + "\n");
            }
            ConCloMySql.conn.commit();
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            try {
                ConCloMySql.conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ordNums;
    }
}
