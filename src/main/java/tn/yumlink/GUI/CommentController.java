package tn.yumlink.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.yumlink.models.Comment;

public class CommentController {
    @FXML
    private VBox commentContainer;

    @FXML
    private Label commentDate;

    @FXML
    private Label commentText;

    @FXML
    private Label userName;
    public void set_comment(Comment comment){
        commentText.setText(comment.getComment_text());
        commentDate.setText(comment.getComment_date().toString());
        userName.setText(comment.getUser().getNom() + " " + comment.getUser().getPrenom());
    }
}
