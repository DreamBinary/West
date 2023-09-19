package org.example.dao;

import java.sql.*;

/**
 * @author CXQ
 * @date 2021/12/21
 */
public class DeleteMySql {

    public void delete(String type, String number) {
        String sql = null;
        String str = null;
        if ("order".equals(type)) {
            str = "订单编号";
        } else if ("goods".equals(type)) {
            str = "商品编号";
        }
        sql = "DELETE FROM `" + type + "` WHERE  `" + str + "` = '" + number + "';";
        try {
            PreparedStatement preStmt = ConCloMySql.conn.prepareStatement(sql);
            ;
            preStmt.executeUpdate();
            System.out.println(".删除数据成功！");
            ConCloMySql.conn.commit();
            preStmt.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(".有订单中含有此商品暂不可删除！！！（需先删除订单信息）");
            System.out.println("..订单编号如下：");
            delRead(number);
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

    public void delRead(String goodNumber) {
        try {
            Statement stmt = ConCloMySql.conn.createStatement();
            String sql = "SELECT * FROM `order` WHERE `商品信息` = '" + goodNumber + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String orderNumber = rs.getString("订单编号");
                System.out.println(".." + orderNumber);
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
    }
}
