package org.example;

import entities.Participant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ParticipantS;

import java.sql.SQLException;


public class ParticipationCard {

    @FXML
    private Label DefiPartt;

    @FXML
    private Label idPartt;

    @FXML
    private Button vote;

    @FXML
    private ImageView imagePart;

    @FXML
    private Label votee;
    @FXML
    private Button suppP;
    private Participant participant;
    private AfficherParticipation afficherPartic;



    public void setD(Participant participant) {
        this.participant=participant;
    }
    public void setData(Participant participant) {
        DefiPartt.setText(participant.getD().getNom_d() );
        idPartt.setText(participant.getU().getNom() +" "+ participant.getU().getPrenom());
        javafx.scene.image.Image image = new Image("file:" + participant.getPhoto_p());
        imagePart.setImage(image);
        votee.setText(Integer.toString(participant.getVote()));


    }



    public void setRefresh(AfficherParticipation afficherPartic) {
        this.afficherPartic=afficherPartic;
    }
    @FXML
    void SupprimerPart(ActionEvent event) {
        try {
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Boîte de dialogue de confirmation");
            alert.setHeaderText("Confirmer la suppression");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce participant ?");

            // Show the confirmation dialog and wait for user response
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            // If the user confirms deletion, proceed with deletion
            if (result == ButtonType.OK) {
                ParticipantS PS = new ParticipantS();
                PS.supprimer(participant.getIdpart());

                afficherPartic.refreshView();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle delete error
        }
    }
    @FXML
    public void voter(ActionEvent event) {

        try {
            // Increment the vote count in the database
            ParticipantS PS = new ParticipantS();
            PS.incrementVote(participant.getIdpart());

            // Update the label
            int newVoteCount = participant.getVote() + 1;
            participant.setVote(newVoteCount);
            votee.setText(Integer.toString(newVoteCount));

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database update error
        }

    }
}
