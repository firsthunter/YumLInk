package com.example.yumlink;

import entities.produit;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell extends TableCell<produit, String> {

    private final ImageView imageView = new ImageView();
    private final double imageSize = 200.0;

    public ImageTableCell() {
        setGraphic(imageView);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.imageView.setFitWidth(imageSize);
        this.imageView.setFitHeight(imageSize);
    }

    @Override
    protected void updateItem(String imagePath, boolean empty) {
        super.updateItem(imagePath, empty);

        if (empty || imagePath == null) {
            setGraphic(null);
        } else {
            // Assuming the imagePath is a file path
            Image image = new Image("file:" + imagePath);
            imageView.setImage(image);
            setGraphic(imageView);
        }
    }
}
