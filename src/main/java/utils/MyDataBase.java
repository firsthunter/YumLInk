package utils;

import java.sql.*;

public class MyDataBase {  final String url="jdbc:mysql://localhost:3306/yumlink";
    final String user="root";
    final String pass="";
    private Connection connection;
    private static MyDataBase instance;
    private MyDataBase(){
        try{
            connection= DriverManager.getConnection(url,user,pass);
            System.out.println("Connected");
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    public static MyDataBase getInstance(){
        if(instance==null)
            instance=new MyDataBase();
        return instance;
    }
    public Connection getConnection(){return connection;}
}
