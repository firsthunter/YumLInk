package services;

import entities.Participant;

import java.sql.SQLException;
import java.util.List;

public interface PService <T>{
    public void ajouterP(T t) throws SQLException;
   // public void modifier(T t) throws SQLException;
    public void supprimer(int id) throws SQLException;

    public List<Participant> afficher () throws SQLException;
}
