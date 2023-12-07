/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package TestProject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnong04
 */
public class LoggedInController implements Initializable {

@FXML
private Button button_logout;
@FXML
private Label label_welcome;

private Stage stage;
private Scene scene;
private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        button_logout.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                MySQLController.changeScene(event, "LogIn.fxml", "Log In!", null);
            }
            
        });
    }    
    
    public void setUserInfo (String username){
        label_welcome.setText("Welcome " + username + "!" );
    }
    
//    public void switchToLogIn (ActionEvent e) throws IOException{
//    Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
//    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
//    Scene scene = new Scene(root);
//    stage.setScene(scene);
//    stage.show();
}

