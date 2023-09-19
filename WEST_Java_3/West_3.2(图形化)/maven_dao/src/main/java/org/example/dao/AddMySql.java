package org.example.dao;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author CXQ
 * @date 2021/12/21
 */
public class AddMySql {

    String type;
    String goodNumber;
    String name_orderNumber;
    String price_time;

    public AddMySql(String type, String name_orderNumber, String goodNumber, String price_time) {
        this.type = type;
        this.goodNumber = goodNumber;
        this.name_orderNumber = name_orderNumber;
        this.price_time = price_time;
        //订单和商品两种输入
    }

    public void add() {
        String sql = null;
        if ("order".equals(type)) {
            sql = "INSERT INTO `order`\n" +
                    "(订单编号,商品信息,下单时间)\n" +
                    "VALUES\n" +
                    "('" + name_orderNumber + "', '" + goodNumber + "', '" + price_time + "');";
        } else if ("goods".equals(type)) {
            sql = "INSERT INTO goods" +
                    "(商品编号,商品名,商品价格)" +
                    "VALUES " +
                    "('" + goodNumber + "', '" + name_orderNumber + "', " + price_time + ");";
        }
        try {
            PreparedStatement preStmt = ConCloMySql.conn.prepareStatement(sql);

            if (preStmt.executeUpdate() == 1) {
                JOptionPane.showMessageDialog(null, "添加成功", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            }
            ConCloMySql.conn.commit();
            preStmt.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            String str = e.getMessage();
            String orderOnly1 = "Duplicate entry 'ord.*' for key 'order.订单唯一'";
            String orderOnly2 = "Duplicate entry 'ord.*' for key 'order.PRIMARY'";
            String goodOnly1 = "Duplicate entry '.*' for key 'goods.商品唯一'";
            String goodOnly2 = "Duplicate entry '.*' for key 'goods.PRIMARY'";
            String goodExist ="Cannot add or update a child row: a foreign key constraint fails (`west`.`order`, CONSTRAINT `order_ibfk_1` FOREIGN KEY (`商品信息`) REFERENCES `goods` (`商品编号`))";
            if (str.matches(orderOnly1)) {
                JOptionPane.showMessageDialog(null, "添加失败 此订单已存在", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (str.matches(orderOnly2)) {
                JOptionPane.showMessageDialog(null, "添加失败 此订单已存在", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (goodExist.equals(str)) {
                JOptionPane.showMessageDialog(null, "添加失败 此商品不存在", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else if (str.matches(goodOnly1) || str.matches(goodOnly2)) {
                JOptionPane.showMessageDialog(null, "添加失败 此商品已存在", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
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

