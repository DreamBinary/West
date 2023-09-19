package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author CXQ
 * @date 2022/1/16
 */
public class CityCheck {

    public boolean check(String city) {
        try {
            Statement stmt = ConCloMySql.conn.createStatement();
            String sql = "SELECT * FROM `citys` WHERE `name` = '" + city + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
