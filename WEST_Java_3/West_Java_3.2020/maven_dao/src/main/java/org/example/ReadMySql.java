package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CXQ
 * @date 2022/1/16
 */
public class ReadMySql {

    public Map readCity(String city) {
        Statement stmt = null;
        try {
            stmt = ConCloMySql.conn.createStatement();
            String sql = "SELECT * FROM `citys` WHERE `name` = '" + city + "';";
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            Map<String,String> map = new HashMap<String, String>(1);
            while (rs.next()) {
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    map.put(data.getColumnName(i), rs.getString(i));
                }
            }
            rs.close();
            stmt.close();
            return map;
        } catch (SQLIntegrityConstraintViolationException sqle) {
            System.out.println("asdfasd-+");
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
        return null;
    }

    public ArrayList readWeather(String city) {
        Statement stmt = null;

        try {
            stmt = ConCloMySql.conn.createStatement();
            String sql = "SELECT * FROM weather WHERE `name` = '"+ city + "'\n" +
                    "AND DATEDIFF(fxDate, NOW()) <= 3 \n" +
                    "AND DATEDIFF(fxDate, NOW()) >= 0";
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            ArrayList<Map> maps = new ArrayList<Map>();
            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    map.put(data.getColumnName(i), rs.getString(i));
                }
                maps.add(map);
            }

            rs.close();
            stmt.close();
            return maps;
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
        return null;

    }


}
