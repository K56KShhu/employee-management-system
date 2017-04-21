package com.zkyyo.www.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 封装数据库连接的相关操作
 */
public class DbConn {
    /**
     * 驱动名称
     */
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    /**
     * != null &&
     * 数据库URL
     */
    private static final String DB_URL = "jdbc:mysql://localhost/test2?useSSL=true";

    /**
     * 数据库用户名
     */
    private static String USER = "root";
    /**
     * 数据库密码
     */
    private static String PASS = "qaws";

    /**
     * 获得一个数据库的连接
     *
     * @return 一个数据库的连接
     */
    public static Connection getConn() {
        Connection conn = null;

        try {
            //注册JDBC驱动
            Class.forName(JDBC_DRIVER);
            //获得数据库连接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
