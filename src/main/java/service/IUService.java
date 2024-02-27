package service;

import java.sql.SQLException;
import java.util.List;

public interface IUService <T> {
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int id)throws SQLException;
    List<T> consulter() throws SQLException;
    T getUserDetailsById(int id) throws SQLException;
    T getUserByEmailAndPwd(String email,String mdp) throws SQLException;
    boolean isAdmin(String email, String mdp) throws SQLException;
}
