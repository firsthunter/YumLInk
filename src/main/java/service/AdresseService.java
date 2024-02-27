package service;

import entities.Adresse;
import entities.User;
import utils.MyDataBase;

import java.sql.*;
import java.util.List;

public class AdresseService implements IAService<Adresse>{
    private Connection connection;

    public AdresseService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public int ajouterAdresse(Adresse adresse) throws SQLException {
        int idA= -1; // Valeur par défaut pour indiquer un échec

        String sql = "INSERT INTO adresse (gouvernorat, ville, rue, codePostal) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, adresse.getGouvernorat());
            preparedStatement.setString(2, adresse.getVille());
            preparedStatement.setString(3, adresse.getRue());
            preparedStatement.setInt(4, adresse.getCodePostal());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idA = generatedKeys.getInt(1); // Récupérer l'ID généré
                    } else {
                        throw new SQLException("Échec de la récupération de l'ID de l'adresse.");
                    }
                }
            }
        }

        return idA;
    }

    @Override
    public void modifierAdresse(Adresse adresse) throws SQLException {
        String sql = "update adresse set gouvernorat=?, ville=?, rue=?, codePostal=? where idA=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, adresse.getGouvernorat());
        preparedStatement.setString(2, adresse.getVille());
        preparedStatement.setString(3, adresse.getRue());

        preparedStatement.setInt(4, adresse.getCodePostal());

        preparedStatement.setInt(5, adresse.getIdA());

        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimerAdresse(int id) throws SQLException {

    }

    @Override
    public List<Adresse> consulterAdresse() throws SQLException {
        return null;
    }

    @Override
    public Adresse getAdresseDetailsById(int idA) throws SQLException {
        String query = "SELECT * FROM adresse WHERE idA = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idA);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {


                    Adresse a=new Adresse();
                    a.setIdA(rs.getInt("idA"));
                    a.setGouvernorat(rs.getString("gouvernorat"));
                    a.setVille(rs.getString("ville"));
                    a.setRue(rs.getString("rue"));
                    a.setCodePostal(rs.getInt("codePostal"));


                    return a;
                } else {

                    throw new RuntimeException("Utilisateur non trouvé pour l'ID : " + idA);
                }
            }
        }
    }
}
