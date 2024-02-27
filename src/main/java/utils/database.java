package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    public Connection getConn() {
        return conn;
    }


    private Connection conn;
    static database instance;
    final String user = "root";
    final String pwd = "";
    final String url = "jdbc:mysql://localhost:3306/yumlink";
    private database() throws SQLException {
        conn= DriverManager.getConnection(url,user,pwd);
        System.out.println("connected");
    }
    public static database getInstance() throws SQLException {
        if (instance == null)
            instance = new database();
        return instance;
    }
}
