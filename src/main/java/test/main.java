package test;

import entities.Adresse;
import entities.User;
import service.AdresseService;
import service.UserService;
import utils.MyDataBase;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        //MyDataBase d = MyDataBase.getInstance();
        UserService us=new UserService();
        AdresseService as=new AdresseService();
        /*try{
            as.ajouterAdresse(new Adresse("Ariana","menzah8","rue khalij",888));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try{
            us.ajouter(new User("emir","ferchichi","emir@gmail.com","emna",98647852,"chef",1,"aaa"));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try{as.modifierAdresse(new Adresse(""));}
        /*try {
            us.modifier(new User(5,"emir","fer","azerr","aaa",98755413,"client","ariana","aaaa"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            us.supprimer(7);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        try{
            System.out.println(us.consulter());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }*/
    }
}
