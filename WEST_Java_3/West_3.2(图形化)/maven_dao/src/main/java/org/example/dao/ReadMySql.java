package org.example.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author CXQ
 * @date 2021/12/19
 */
public class ReadMySql {
    String type;
    String number;

    public ReadMySql(String type, String number) {
        this.type = type;
        this.number = number;
    }

    public String read() {
        // 执行查询
        Statement stmt = null;
        String text = "";
        try {
            stmt = ConCloMySql.conn.createStatement();
            String sql = null;
            if ("order".equals(type)) {
                sql = "SELECT b.`订单编号`, a.`商品编号`,a.`商品名`, a.`商品价格`, b.`下单时间`" +
                        "FROM goods a LEFT JOIN `order` b ON a.`商品编号` = b.`商品信息` " +
                        "WHERE b.`订单编号` = '" + number + "'";
            } else if ("goods".equals(type)) {
                sql = "SELECT  * FROM `goods` WHERE `商品编号` = " + number;
            }
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            if (rs.next()) {
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    text = text + data.getColumnName(i) + " : " + rs.getString(i) + '\n';
                }
            }
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
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }
}
