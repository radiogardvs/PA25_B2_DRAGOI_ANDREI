package com.pa.laboratory9.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties props = new Properties();
        try {
            props.load(JdbcUtil.class.getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load app.properties", e);
        }
        URL = props.getProperty("jdbc.url");
        USER = props.getProperty("jdbc.user");
        PASSWORD = props.getProperty("jdbc.password");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
