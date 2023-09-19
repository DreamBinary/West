package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author CXQ
 * @date 2022/1/13
 */
public class GetCityId {

    public String getCityId(String city) {
        Statement stmt = null;
        String id = null;
        try {
            stmt = ConCloMySql.conn.createStatement();
            String sql = "SELECT id FROM `citys` WHERE `name` = '" + city + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                id = rs.getString("id");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        return id;
    }
}
