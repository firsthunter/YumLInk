package service;

import entities.User;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUService<User> {
    private Connection connection;

    public UserService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String sql = "INSERT INTO user (nom, prenom, email, mdp, tel,role,Image,idA) " +
                "VALUES ('" + user.getNom() + "', '" + user.getPrenom() + "', '" +
                user.getEmail() + "', '" + user.getMdp() + "', '" + user.getTel() + "', '" + user.getRole() + "', '" + user.getImage() + "', '" + user.getIdA() + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }


    @Override
    public void modifier(User user) throws SQLException {
        String sql = "update user set nom=?, prenom=?, email=?, mdp=?, tel=?, role=?,Image=?,idA=? where idU=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, user.getNom());
        preparedStatement.setString(2, user.getPrenom());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getMdp());
        preparedStatement.setInt(5, user.getTel());
        preparedStatement.setString(6, user.getRole());
        preparedStatement.setString(7, user.getImage());
        preparedStatement.setInt(8, user.getIdA());
        preparedStatement.setInt(9, user.getIdU());

        preparedStatement.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `user` WHERE idU=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<User> consulter() throws SQLException {
        String sql = "select * from user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<User> list = new ArrayList<>();
        while (rs.next()) {
            User u = new User();
            u.setIdU(rs.getInt("idU"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setMdp(rs.getString("mdp"));
            u.setTel(rs.getInt("tel"));
            u.setRole(rs.getString("role"));
            u.setImage(rs.getString("Image"));
            u.setIdA(rs.getInt("idA"));

            list.add(u);
        }
        return list;
    }

    @Override
    public User getUserDetailsById(int idU) throws SQLException {
        String query = "SELECT * FROM user WHERE idU = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idU);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {


                    User u = new User();
                    u.setIdU(rs.getInt("idU"));
                    u.setNom(rs.getString("nom"));
                    u.setPrenom(rs.getString("prenom"));
                    u.setEmail(rs.getString("email"));
                    u.setMdp(rs.getString("mdp"));
                    u.setTel(rs.getInt("tel"));
                    u.setRole(rs.getString("role"));
                    u.setImage(rs.getString("Image"));
                    u.setIdA(rs.getInt("idA"));

                    return u;
                } else {

                    throw new RuntimeException("Utilisateur non trouvé pour l'ID : " + idU);
                }
            }
        }
    }

    @Override
    public User getUserByEmailAndPwd(String email, String mdp) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ? AND mdp= ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, mdp);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {


                    User u = new User();
                    u.setIdU(rs.getInt("idU"));
                    u.setNom(rs.getString("nom"));
                    u.setPrenom(rs.getString("prenom"));
                    u.setEmail(rs.getString("email"));
                    u.setMdp(rs.getString("mdp"));
                    u.setTel(rs.getInt("tel"));
                    u.setRole(rs.getString("role"));

                    u.setImage(rs.getString("Image"));
                    u.setIdA(rs.getInt("idA"));

                    return u;
                } else {

                    throw new RuntimeException("Utilisateur non trouvé par email : " + email);
                }
            }

        }
    }
    public boolean isAdmin(String email, String mdp) throws SQLException {
        String sql = "SELECT * FROM admin WHERE emailA = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, mdp);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
   }
    public boolean emailExiste(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }

        return false;
    }
}