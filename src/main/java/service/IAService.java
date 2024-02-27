package service;

import java.sql.SQLException;
import java.util.List;

public interface IAService <T>{
    int ajouterAdresse(T t) throws SQLException;
    void modifierAdresse(T t) throws SQLException;
    void supprimerAdresse(int id)throws SQLException;
    List<T> consulterAdresse() throws SQLException;
    T getAdresseDetailsById(int id) throws SQLException;
}
