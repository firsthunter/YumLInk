package services;
import entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.database;
public class produitserv implements Iservice<produit> {

    private Connection conn;
    public produitserv() throws SQLException {


        conn= database.getInstance().getConn();

    }

    @Override
    public void ajouterproduit(produit produit) throws SQLException {

        String sql = "INSERT INTO produit (nom, prix, diescription, image) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, produit.getNom());
            preparedStatement.setDouble(2, produit.getPrix());
            preparedStatement.setString(3, produit.getDiescription());
            preparedStatement.setString(4, produit.getImage());

            preparedStatement.executeUpdate();
        }
    }
    @ Override
    public void updateProduit(produit produit) throws SQLException {
        String sql = "UPDATE produit SET nom=?, prix=?, diescription=? ,image=? WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, produit.getNom());
            pstmt.setFloat(2, produit.getPrix());
            pstmt.setString(3, produit.getDiescription());
            pstmt.setString(4, produit.getImage());
            pstmt.setInt(5, produit.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteproduit(int id) throws SQLException {

        String sql = "DELETE FROM produit WHERE id = " + id;
        Statement ste = conn.createStatement();
        ste.executeUpdate(sql);
        ste.close();
    }

    @Override
    public List<produit> afficherProduit() throws SQLException {

        Statement st = conn.createStatement();
        String req = "SELECT * FROM produit";
        List <produit> produit = new ArrayList<>();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()){
            produit p1 = new produit();
            p1.setId(rs.getInt("id"));
            p1.setPrix(rs.getInt("prix"));
            p1.setNom(rs.getString("nom"));
            p1.setDiescription(rs.getString("diescription"));
            p1.setImage(rs.getString("image"));
            produit.add(p1);
        }
        return produit;

    }
}
