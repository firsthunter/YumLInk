package org.example;

import entities.Participant;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class ChallP {

    @FXML
    private Label DefiPartt;

    @FXML
    private Label idPartt;

    @FXML
    private ImageView imagePart;

    @FXML
    private Label votee;
    private Participant participant;
    private AfficherPartic afficherPartic;

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



    public void setRefresh(AfficherPartic afficherPartic) {
        this.afficherPartic=afficherPartic;
    }
}
