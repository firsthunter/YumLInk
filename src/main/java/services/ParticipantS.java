package services;

import entities.Défis;
import entities.Participant;
import entities.User;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantS implements PService<Participant> {

    Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public ParticipantS(){
        conn= MyDB.getInstance().getConn();
    }

    @Override
    public void ajouterP(Participant participant) throws SQLException {
        String req = "INSERT INTO participant ( photo_p,idU,id_d,vote) " +
                "VALUES (?, ?, ?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setString(1, participant.getPhoto_p());
        preparedStatement.setInt(2,participant.getU().getIdU());
        preparedStatement.setInt(3,participant.getD().getId_d());
        preparedStatement.setInt(4,participant.getVote());
        preparedStatement.executeUpdate();
    }
  /*  @Override
    public void modifier(Participant participant) throws SQLException {
        String sql = "update participant set photo_p = ?  where idpart = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, participant.getPhoto_p());
        preparedStatement.setInt(2, participant.getIdpart());
        preparedStatement.executeUpdate();
    }*/

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `participant` WHERE idpart=?";
        PreparedStatement preparedStatement = conn.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Participant> afficher() throws SQLException {
        Statement st = conn.createStatement();
        String req = "SELECT p.* , u.nom , u.prenom , u.idU ,d.nom_d ,d.id_d FROM participant p " +
                "INNER JOIN user u ON p.idU = u.idU " +
                "INNER JOIN defis d ON p.id_d = d.id_d";
        List <Participant> par = new ArrayList<>();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()){
            Participant p1 = new Participant();
            p1.setIdpart(rs.getInt("idpart"));
            p1.setPhoto_p(rs.getString("photo_p"));
            p1.setVote(rs.getInt("vote"));


            User user = new User(rs.getInt("idU"),rs.getString("nom"),rs.getString("prenom"));
            p1.setU(user);
            Défis defis = new Défis(rs.getInt("id_d"),rs.getString("nom_d"));
            p1.setD(defis);
            par.add(p1);

        }
        return par;
    }

    public void incrementVote(int idpart)  throws  SQLException{

        String sql = "UPDATE participant SET vote = vote + 1 WHERE idpart = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, idpart);
            statement.executeUpdate();
        }
    }


    }

