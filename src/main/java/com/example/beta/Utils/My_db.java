package com.example.beta.Utils;
import java.sql.*;
public class My_db {
    Connection conn ;
    String  url = "jdbc:mysql://localhost:3306/yumlink";
    String  user = "root";
    String  pwd  = "";
    Statement  ste;
    static My_db instance;

    private My_db() {
        try {
            conn=DriverManager.getConnection(url , user , pwd);
            System.out.println("connected");
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }


    }
    public static  My_db getInstance(){
        if (instance ==null)
            instance=new My_db();
        return  instance;
    }

    public Connection getConn() {
        return conn;
    }
}
