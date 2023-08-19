package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenu extends Application {
	 Stage anotherStage = new Stage();
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("start2.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setX(0);
        primaryStage.setTitle("Car Rental Company");
        primaryStage.setX(300);
        primaryStage.setY(80);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
       
    }

    public static void main(String[] args) {
        launch(args);
    }
}
