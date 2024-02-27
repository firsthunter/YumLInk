package Services;

import Entities.Ingredient;
import Entities.Recettes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utiles.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientS implements Service<Ingredient>{

    Connection conn;
    public IngredientS(){
        conn= DataBase.getInstance().getConn();
    }


    @Override
    public void ajouter(Ingredient ingredient) throws SQLException {

        String sql = "insert into ingredient (id_ing, nom, quantite) " +
                "values('" + ingredient.getId_ing() + "','" + ingredient.getNom() + "'"
                +  "," + ingredient.getQuantite() + ")";

        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }
    @Override
    public void modifier(Ingredient ingredient) throws SQLException {
        String sql = "update ingredient set  nom = ?, quantite = ? where id_ing = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, ingredient.getNom());
        preparedStatement.setInt(2, ingredient.getQuantite());
        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM ingredient WHERE id_ing = " + id;
        Statement ste = conn.createStatement();
        ste.executeUpdate(sql);
        ste.close();
    }

    @Override
    public List<Ingredient> afficher() throws SQLException {
        Statement st = conn.createStatement();
        String req = "SELECT * FROM ingredient";
        List<Ingredient> List = new ArrayList<>();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Ingredient r = new Ingredient();
            r.setId_ing(rs.getInt("id_ing"));
            r.setNom(rs.getString("nom"));
            r.setQuantite(rs.getInt("quantite"));

            List.add(r);
        }
        return List;

    }




    //@Override
   // public List<Ingredient> afficher() throws SQLException {}


}
