package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author CXQ
 * @date 2021/12/21
 */
public class ConCloMySql {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/west";

    static final String USER = "root";
    static final String PASS = "mysqlcxq";

    public static Connection conn = null;

    public void connect() {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println(".连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        System.out.println(".Goodbye!");
    }
}
