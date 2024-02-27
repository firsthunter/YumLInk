package services;
import entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.database;

public class panierserv implements Iservicee<panier> {


    private Connection conn;
    public panierserv() throws SQLException {


        conn= database.getInstance().getConn();

    }
    @Override
    public void ajouterpanier(panier panier) throws SQLException {
        String sql = "INSERT INTO panier (quantite, prixtotal) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, panier.getQuantite());
            preparedStatement.setDouble(2, panier.getPrixtotal());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updatepanier(panier panier) throws SQLException {
        String sql = "UPDATE panier SET quantite=?, prixtotal=? WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, panier.getQuantite());
            pstmt.setFloat(2, panier.getPrixtotal());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deletepanier(int id) throws SQLException {
        String sql = "DELETE FROM panier WHERE id = " + id;
        Statement ste = conn.createStatement();
        ste.executeUpdate(sql);
        ste.close();
    }

    @Override
    public List<panier> afficherpanier() throws SQLException {
        Statement st = conn.createStatement();
        String req = "SELECT * FROM panier";
        List <panier> panier = new ArrayList<>();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()){
            panier p1 = new panier();
            p1.setIdp(rs.getInt("idp"));
            p1.setPrixtotal(rs.getFloat("prixtotal"));
            p1.setQuantite(rs.getInt("quantite"));
            p1.setId_client(rs.getInt("id_client"));
            p1.setId_prduit(rs.getInt("id_produit"));
            panier.add(p1);
        }
        return panier;

    }
    }

