package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController{
   
    public void ret(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Stage stage=new Stage();
        stage.setX(300);
        Scene scene = new Scene(root);
        stage.setTitle("Tarifi Motors");
        stage.setScene(scene);
        stage.show();
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
        
    }
    public void team(ActionEvent actionEvent) throws IOException {
    Parent anotherRoot = FXMLLoader.load(getClass().getResource("/view/whoAreWe.fxml")); // FXML for second stage
    Scene anotherScene = new Scene(anotherRoot);
    Stage anotherStage=new Stage();
    anotherStage.setTitle("Who Are We");
    anotherStage.setX(300);
    anotherStage.setScene(anotherScene);
    anotherStage.show();
    
    }
 
}

