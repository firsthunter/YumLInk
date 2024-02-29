package tn.yumlink.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tn.yumlink.models.Comment;
import tn.yumlink.services.CommentService;

import java.sql.SQLException;
import java.util.Optional;

public class CommentController {
    @FXML
    private VBox commentContainer;

    @FXML
    private Label commentDate;

    @FXML
    private Label commentText;

    @FXML
    private Label userName;
    private Comment comment;
    CommentService commentService = new CommentService();
    private PostPageController postPageController;

    public void setPostPageController(PostPageController postPageController) {
        this.postPageController = postPageController;
    }

    public void set_comment(Comment comment) {
        this.comment = comment;
        commentText.setText(comment.getComment_text());
        commentDate.setText(comment.getComment_date().toString());
        userName.setText(comment.getUser().getNom() + " " + comment.getUser().getPrenom());
    }

    public void handleDelete() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete this comment?");
        ButtonType yesButtonType = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButtonType = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(yesButtonType, noButtonType);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == yesButtonType) {
            try {
                commentService.deleteComment(comment.getComment_id());
                postPageController.fetchCommentsById(comment.getArticle_id());
            } catch (SQLException e) {
                e.printStackTrace();
                // Display an error message or handle the exception as needed
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Deletion Failed");
                errorAlert.setContentText("An error occurred while deleting the comment. Please try again.");
                errorAlert.showAndWait();
            }
        }
    }
}
