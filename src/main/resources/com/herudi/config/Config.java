/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package herudi.config;

import java.io.IOException;

import herudi.exception.JMetroRuntimeException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Herudi
 */
public class Config
{
  public static void dialog(Alert.AlertType alertType, String alertMessage)
  {
    Alert alert = new Alert(alertType, alertMessage);
    alert.initStyle(StageStyle.UTILITY);
    alert.setTitle("Info");
    alert.showAndWait();
  }

  public void newStage(Labeled node, String load, String title, boolean resize, StageStyle style, boolean maximized)
  {
    Stage stage;
    try
    {
      Stage st = new Stage();
      stage = (Stage) node.getScene().getWindow();
      Parent root = FXMLLoader.load(getClass().getResource(load));
      Scene scene = new Scene(root);
      st.initStyle(style);
      st.setResizable(resize);
      st.setMaximized(maximized);
      st.setTitle(title);
      st.setScene(scene);
      st.show();
      stage.close();
    }
    catch (Exception e)
    {
      throw new JMetroRuntimeException("Error occurred while trying to create stage", e);
    }
  }

  public void loadAnchorPane(AnchorPane anchorPane, String path)
  {
    try
    {
      AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
      anchorPane.getChildren().setAll(pane);
    }
    catch (IOException e)
    {
      throw new JMetroRuntimeException("Error occurred while trying to load AnchorPane", e);
    }
  }

  public static void setModelColumn(TableColumn tableColumn, String property)
  {
    tableColumn.setCellValueFactory(new PropertyValueFactory(property));
  }
}
