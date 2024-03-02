package services;

import entities.*;

import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DéfisS implements IService<Défis>  {
    Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public DéfisS(){
        conn= MyDB.getInstance().getConn();
    }



    @Override
    public void ajouter(Défis défis) throws SQLException {
        String req = "INSERT INTO defis ( nom_d,photo_d,dis_d, delai,  heure, idU) " +
                "VALUES (?, ?, ?, ?,? ,? )";
        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setString(1, défis.getNom_d());
        preparedStatement.setString(2, défis.getPhoto_d());
        preparedStatement.setString(3, défis.getDis_d().toString());
        preparedStatement.setDate(4, java.sql.Date.valueOf(défis.getDelai()));
        preparedStatement.setTime(5, java.sql.Time.valueOf(défis.getHeure()));
        preparedStatement.setInt(6,défis.getUser().getIdU());

        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Défis défis) throws SQLException {
        String sql = "update defis set nom_d = ?,photo_d = ?, dis_d = ?, delai = ?, heure= ?  where id_d = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, défis.getNom_d());
        preparedStatement.setString(2, défis.getPhoto_d());
        preparedStatement.setString(3,défis.getDis_d());
        preparedStatement.setDate(4, java.sql.Date.valueOf(défis.getDelai()));
        preparedStatement.setTime(5, java.sql.Time.valueOf(défis.getHeure()));
        preparedStatement.setInt(6, défis.getId_d());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `defis` WHERE id_d=?";
        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }





    @Override    public List<Défis> afficher() throws SQLException{

        Statement st = conn.createStatement();
        String req = "SELECT d.* , u.nom , u.prenom , u.idU FROM defis d " +
                "INNER JOIN user u ON d.idU = u.idU";
        List <Défis> def = new ArrayList<>();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()){
            Défis d1 = new Défis(21);
            d1.setId_d(rs.getInt("id_d"));
            d1.setPhoto_d(rs.getString("photo_d"));
            d1.setNom_d(rs.getString("nom_d"));
            d1.setDis_d(rs.getString("dis_d"));
            d1.setDelai(rs.getDate("delai").toLocalDate());
            d1.setHeure(rs.getTime("heure").toLocalTime());
            User user = new User(rs.getInt("idU"),rs.getString("nom"),rs.getString("prenom"));
            d1.setUser(user);
            def.add(d1);
        }
        return def;

    }
    }



