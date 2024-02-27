package Controllers;

import Entities.Recettes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RecepieContainer {

    @FXML
    private ImageView img_r;

    @FXML
    private Label label_cal;

    @FXML
    private Label label_desc;

    @FXML
    private Label label_nom;

    @FXML
    private Label label_prot;

    @FXML
    private Button showmore;
    @FXML
    private Button update_btn;
    @FXML
    private Button delete_btn;

    Recettes rs ;
    ClientView pageH ;
    public void set_Recttes (Recettes rs) {

        label_nom.setText(rs.getNom());
        label_desc.setText(rs.getDescription());
        label_prot.setText(Integer.toString(rs.getProtein()));
        label_cal.setText(Integer.toString(rs.getCalorie()));
        Image img = new Image ("/placeholder.jpg") ;
        img_r.setImage(img);


    }


}
