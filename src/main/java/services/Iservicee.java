package services;

import java.sql.SQLException;
import java.util.List;

public interface Iservicee <T> {

    public void ajouterpanier(T t) throws SQLException;

    public void updatepanier(T t) throws SQLException;

    public void deletepanier(int id) throws SQLException;

    public List<T> afficherpanier() throws SQLException;

}
