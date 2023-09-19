package org.example;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author CXQ
 * @date 2021/12/21
 */
public class AddMySql {

    public void addCity(String name, String id, String lat, String lon) {
        String sql = "INSERT INTO `citys`\n" +
                "(`name`, id, lat, lon)\n" +
                "VALUES\n" +
                "('" + name + "', '" + id + "', '" + lat + "', '" + lon + "')";
        try {
            PreparedStatement preStmt = ConCloMySql.conn.prepareStatement(sql);
            preStmt.executeUpdate();
            ConCloMySql.conn.commit();
            preStmt.close();
        } catch (SQLIntegrityConstraintViolationException s){
            //已经存在的情况 不显示异常
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

    public void addWeather(String name, String fxDate, String tempMax, String tempMin, String textDay) {
        String sql = "INSERT INTO `weather`\n" +
                "(`name`,fxDate,tempMax,tempMin, textDay)\n" +
                "VALUES\n" +
                "('" + name + "','" + fxDate + "','" + tempMax + "','" + tempMin + "', '" + textDay + "')";
        try {
            PreparedStatement preStmt = ConCloMySql.conn.prepareStatement(sql);
            preStmt.executeUpdate();
            ConCloMySql.conn.commit();
            preStmt.close();
       } catch (SQLIntegrityConstraintViolationException s){
            //已经存在的情况 不显示异常
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
