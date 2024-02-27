package services;

import java.sql.SQLException;
import java.util.List;

public interface Iservice<T> {

    public void ajouterproduit(T t) throws SQLException;

    public void updateProduit(T t) throws SQLException;

    public void deleteproduit(int id) throws SQLException;

    public List<T> afficherProduit() throws SQLException;

}
