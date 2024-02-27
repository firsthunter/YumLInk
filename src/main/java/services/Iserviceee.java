package services;

import java.sql.SQLException;
import java.util.List;

public interface Iserviceee <T> {

    public void ajoutercommand(T t) throws SQLException;

    public void updatecommand(T t) throws SQLException;

    public void deletecommand(int id) throws SQLException;

    public List<T> affichervommand() throws SQLException;

}
