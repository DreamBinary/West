package org.example.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author CXQ
 * @date 2021/12/22
 */
public class ReadFyMysql {
    String type;
    int onePage;
    Statement stmt;

    {
        try {
            stmt = ConCloMySql.conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ReadFyMysql(String type, int onePage) {
        this.type = type;
        this.onePage = onePage;
    }

    public void getPage(int pageNum) {
        try {
            int pageFront = (pageNum - 1) * onePage;
            String sql = null;
            if ("order".equals(type)) {
                sql = "SELECT b.`订单编号`, a.`商品编号`,a.`商品名`, a.`商品价格`, b.`下单时间`" +
                        "FROM goods a LEFT JOIN `order` b ON a.`商品编号` = b.`商品信息` LIMIT " +
                        pageFront + "," + onePage;
                System.out.printf("|%-12s|%-12s|%-15s|%-15s|%-15s\n", "订单编号", "商品编号", "商品名", "商品价格", "下单时间");
            } else if ("goods".equals(type)) {
                sql = "SELECT * FROM `goods` LIMIT " + pageFront + "," + onePage;
                System.out.printf("|%-12s|%-15s|%-15s\n", "商品编号", "商品名", "商品价格");

            }
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    System.out.printf("|%-15s", rs.getString(i));
                }
                System.out.printf("\n");
            }
            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            try {
                ConCloMySql.conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public int getPageNum() {
        String sql = null;
        int pageCount = 0;
        try {
            if ("order".equals(type)) {
                sql = "SELECT COUNT(*) FROM `goods`;";
            } else if ("goods".equals(type)) {
                sql = "SELECT COUNT(*) FROM `order`;";
            }
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int rowCount = rs.getInt(1);
            pageCount = (int) Math.ceil(1.0 * rowCount / onePage);
            ConCloMySql.conn.commit();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            try {
                ConCloMySql.conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        }
        return pageCount;
    }

}
