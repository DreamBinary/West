package org.example.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author CXQ
 * @date 2021/12/21
 */
public class AddMySql {

    String type;
    String orderNumber;
    String goodNumber;
    String name;
    String price;
    String time;

    public AddMySql(String type, String good_number, String name, String price) {
        this.type = type;
        this.goodNumber = good_number;
        this.name = name;
        this.price = price;
    }

    public AddMySql(String type, String order_number, String good_number, String name, String price, String time) {
        this.type = type;
        this.orderNumber = order_number;
        this.goodNumber = good_number;
        this.name = name;
        this.price = price;
        this.time = time;
    }

    public void add() {
        String sql = null;
        String sql2 = null;
        if ("order".equals(type)) {
            sql = "INSERT INTO goods\n" +
                    "(商品编号,商品名,商品价格)\n" +
                    "VALUES \n" +
                    "('" + goodNumber + "', '" + name + "', '" + price + "');";
            sql2 = "INSERT INTO `order`\n" +
                    "(订单编号,商品信息,下单时间)\n" +
                    "VALUES\n" +
                    "('" + orderNumber + "', '" + goodNumber + "', '" + time + "');";
            try {
                PreparedStatement preStmt = ConCloMySql.conn.prepareStatement(sql);
                PreparedStatement preStmt2 = ConCloMySql.conn.prepareStatement(sql2);
                preStmt.executeUpdate();
                preStmt2.executeUpdate();
                System.out.println(".添加数据成功！");
                ConCloMySql.conn.commit();
                preStmt.close();
                preStmt2.close();
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

        } else if ("goods".equals(type)) {
            sql = "INSERT INTO goods" +
                    "(商品编号,商品名,商品价格)" +
                    "VALUES " +
                    // ('10001','iPad',4799),
                    "('" + goodNumber + "', '" + name + "', " + price + ");";
            try {
                PreparedStatement preStmt = ConCloMySql.conn.prepareStatement(sql);
                preStmt.executeUpdate();
                System.out.println(".添加数据成功！");
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
}
