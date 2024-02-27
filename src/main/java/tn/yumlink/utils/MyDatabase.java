package tn.yumlink.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private Connection connection;
    private static MyDatabase instance;
    final String url = "jdbc:mysql://localhost:3306/yumlink";
    final String user = "root";
    final String pwd = "";
    private MyDatabase() {
        try {
            connection = DriverManager.getConnection(url,user,pwd);
        } catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }
    public static MyDatabase getInstance(){
        if (instance == null)
            instance = new MyDatabase();
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
}
