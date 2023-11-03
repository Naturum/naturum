/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naturum;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ryan Chin
 */
public class MainpageController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void switchToLogin(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTrivia(ActionEvent event) throws IOException{
        //Parent root = FXMLLoader.load(getClass().getResource("trivia.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("trivia.fxml"));
        root = loader.load();
        
        TriviaController triviaController = loader.getController();
        triviaController.displayQuestion();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
