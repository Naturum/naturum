/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trivia;

import Login.User;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Ryan Chin
 */
public class triviaMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML Button triviaButton;
    @FXML Label doneMessage;
    
    //Initialise the page
    public void init(boolean check, int day){
        if (day>10) 
            doneMessage.setText("No more trivias left :(");
        triviaButton.setDisable(check);
        doneMessage.setVisible(check);
    }
    
    //Switch to mainpgae
    public void switchToMainpage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLfiles/LoggedIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    //Switch to trivia records
    public void switchToTriviaRecord(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/triviaRecord.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        triviaRecordController triviaRecordCon = loader.getController();
        triviaRecordCon.unlockRecord(u);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    //switch to trivia
    public void switchToTrivia(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/trivia.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        
        //Get current day to initialise the trivia
        LocalDate now = LocalDate.now();
        int day = (int) ChronoUnit.DAYS.between(u.getRegDate(), now) + 1;
        TriviaController triviaController = loader.getController();
        triviaController.displayQuestion(u, false, day);
        
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
