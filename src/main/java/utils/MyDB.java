package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    static Connection conn;
    final String url="jdbc:mysql://localhost:3306/yumlink";
    final String user="root";
    final String pwd="";
    static MyDB instance;
    private MyDB(){
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connected");
        }catch(SQLException s){
            System.out.println(s.getMessage());
        }
    }
    public static MyDB getInstance(){
        if (instance == null)
        {
            instance = new MyDB();
            return instance;
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }
}
