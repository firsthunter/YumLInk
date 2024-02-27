package Services;


import Entities.Recettes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utiles.DataBase ;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecettesS implements Service<Recettes>{

    Connection conn;
    public RecettesS(){
        conn= DataBase.getInstance().getConn();
    }
    @Override
    public void ajouter(Recettes recette) throws SQLException{
        String sql = "INSERT INTO recettes (nom, description,imgSrc, calorie , protein) VALUES (?, ? , ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, recette.getNom());
            preparedStatement.setString(2, recette.getDescription());
            preparedStatement.setString(3, recette.getImgSrc());
            preparedStatement.setInt(4, recette.getCalorie());
            preparedStatement.setInt(5, recette.getProtein());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(Recettes recettes) throws SQLException {
        String sql = "UPDATE recettes SET nom = ?, description = ?, calorie = ?, protein = ?, imgSrc = ? WHERE id_r = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, recettes.getNom());
        pstmt.setString(2, recettes.getDescription());
        pstmt.setInt(3, recettes.getCalorie());
        pstmt.setInt(4, recettes.getProtein());
        pstmt.setString(5, recettes.getImgSrc());
        pstmt.setInt(6, recettes.getId_r());
        pstmt.executeUpdate();
        pstmt.close();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM recettes WHERE id_r = " + id;
        Statement ste = conn.createStatement();
        ste.executeUpdate(sql);
        ste.close();

    }

    @Override
    public List<Recettes> afficher() throws SQLException{
        Statement st = conn.createStatement();
        String req = "SELECT * FROM recettes";
        List<Recettes> recettesList = new ArrayList<>();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Recettes r = new Recettes();
            r.setId_r(rs.getInt("id_r"));
            r.setNom(rs.getString("nom"));
            r.setDescription(rs.getString("description"));
            r.setCalorie(rs.getInt("calorie"));
            r.setProtein(rs.getInt("protein"));
            r.setImgSrc(rs.getString("imgSrc"));
            recettesList.add(r);
        }
        return recettesList;}



    }


