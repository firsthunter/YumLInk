package services;

import entities.command;
import entities.panier;
import utils.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class commandserv implements Iserviceee<command> {

    private Connection conn;
    public commandserv() throws SQLException {


        conn= database.getInstance().getConn();

    }
    @Override
    public void ajoutercommand(command command) throws SQLException {
        String sql = "INSERT INTO commande (date, id_client,id_panier) VALUES (?, ?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setDate(1, (Date) command.getDate());
            preparedStatement.setInt(2, command.getId_client());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updatecommand(command command) throws SQLException {
        String sql = "UPDATE command SET date=?, id_client=? WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, (Date) command.getDate());
            pstmt.setFloat(2, command.getId_client());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deletecommand(int id) throws SQLException {
        String sql = "DELETE FROM command WHERE id = " + id;
        Statement ste = conn.createStatement();
        ste.executeUpdate(sql);
        ste.close();
    }

    @Override
    public List<command> affichervommand() throws SQLException {
        Statement st = conn.createStatement();
        String req = "SELECT * FROM commande";
        List <command> commands = new ArrayList<>();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()){
            command p1 = new command();
            p1.setId_comm(rs.getInt("id_com"));
            p1.setDate(rs.getDate("date"));
            p1.setId_client(rs.getInt("id_client"));
            commands.add(p1);
        }
        return commands;
    }
}
