package com.ndecs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:postgresql://dpg-d4osll75r7bs73d702a0-a.oregon-postgres.render.com:5432/ndecs_db?sslmode=require";
    private static final String USER = "ndecs_db_user";
    private static final String PASSWORD = "fWQHFTGGGvW1FqrtbPPq4gSEUhdQfWY0"; // <-- Only password here

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
