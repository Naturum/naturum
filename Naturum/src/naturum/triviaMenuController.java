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
public class triviaMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void switchToMainpage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToTriviaRecord(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("triviaRecord.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        triviaRecordController triviaRecordCon = loader.getController();
        triviaRecordCon.unlockRecord(u.getIdUser());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToTrivia(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("trivia.fxml"));
        root = loader.load();
        TriviaController triviaController = loader.getController();
        triviaController.displayQuestion(1, false);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
