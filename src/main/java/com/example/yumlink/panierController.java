package com.example.yumlink;

import entities.panier;

import entities.produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import services.commandserv;
import services.produitserv;
import utils.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

import static com.example.yumlink.sendMail.sendEmail;

public class panierController {
    public panierController() {
    }

    @FXML
    private TableView<produit> tableData;

    @FXML
    private GridPane teethPane;

    @FXML
    private ComboBox<?> toothComboBoxOne;

    @FXML
    private ComboBox<?> toothComboBoxThree;

    @FXML
    private ComboBox<?> toothComboBoxTwo;

    @FXML
    private TextArea toothTextArea;

    @FXML
    private TextField txtId;

    @FXML
    private GridPane typeOnePane;

    @FXML
    private GridPane typeThreePane;

    @FXML
    private GridPane typeTwoPane;

    @FXML
    private VBox vboxCrud;
    @FXML
    private GridPane mainGridPane;
    @FXML
    private TextField priXTotal;

    float pritTT;

    BaseController baseController;

    public void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    @FXML
    private void initialize() {
        Connection con = null;

        try {
            int clientId = 15;  // Replace with your actual client ID
            con = database.getInstance().getConn();

            // Assuming you have a method to get the database connection

            PreparedStatement st = con.prepareStatement(
                    "SELECT produit.* FROM produit " +
                            "JOIN panier ON produit.id = panier.id_produit " +
                            "WHERE panier.id_client = ?");

            st.setInt(1, clientId);
            try (ResultSet rs = st.executeQuery()) {
                // Create an ObservableList to hold your produit objects
                ObservableList<produit> productList = FXCollections.observableArrayList();

                // Assuming produit has fields like "nom", "prix", etc.
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    float prix = rs.getFloat("prix");
                    String description = rs.getString("diescription");
                    String image = rs.getString("image");

                    // Create a produit object and add it to the list
                    produit product = new produit(id, nom, prix, description, image);
                    productList.add(product);
                }

                mainGridPane.getChildren().clear();
                int row = 1;
                int col = 0;

                for (produit product : productList) {
                    FXMLLoader itemLoader = new FXMLLoader(getClass().getResource("pancom.fxml"));
                    Parent interfaceRoot = itemLoader.load();
                    pancomController itemController = itemLoader.getController();
                    itemController.setData(product);

                    mainGridPane.add(interfaceRoot, col, row);
                    mainGridPane.setHgap(40); // Set the horizontal gap between items
                    mainGridPane.setVgap(30); // Set the vertical gap between items

                    col++;
                    if (col == 1) { // Change this value based on your layout
                        col = 0;
                        row++;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setPrixTTValue(float prixTT) {
        priXTotal.setText(String.valueOf(prixTT));
    }

//    @FXML
//    void addcommand(ActionEvent event) throws SQLException {
//        database d= database.getInstance();
//        int clientId = 15;
//
//        commandserv ps=new commandserv();
//        ps.ajoutercommand(new panier(clientId,'24-08-2002',15));
//
//    }


//    public void sendEmail(String sourceEmail, String sourcePwd, String desEmail, String subject, String body) {
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//
//        // Create a mail session
//        Session session = Session.getDefaultInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(sourceEmail, sourcePwd);
//            }
//        });
//
//        // Create a new email message
//        Message message = new MimeMessage(session);
//        try {
//            message.setFrom(new InternetAddress(sourceEmail));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(desEmail));
//            message.setSubject(subject);
//            message.setText(body);
//
//            // Send the email
//            Transport.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @FXML
    private void handleSendEmailButton(ActionEvent event) {
        // Replace these values with your actual email and password
        String sourceEmail = "jihedhorchani@gmail.com";
        String sourcePwd = "pwnpdldwbcokcmcw";

        // Replace this with the recipient's email address
        String desEmail = "esrazitouni99@gmail.com";

        String subject = "teeest";
        String body = "cordiallement";

        // Call the sendEmail method

        sendEmail(sourceEmail, sourcePwd, desEmail, subject, body);
    }
}





