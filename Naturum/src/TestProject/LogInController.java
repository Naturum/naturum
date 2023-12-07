/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package TestProject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBase;

/**
 * FXML Controller class
 *
 * @author johnong04
 */
public class LogInController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML 
    private Button button_login;
    
    @FXML
    private Button button_signup;
    
    @FXML
    private TextField tf_username;
    
    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void logIn (ActionEvent event){
        MySQLController.logInUser(event, tf_username.getText(), tf_password.getText());
    }
    
    public void signUp (ActionEvent event){
        MySQLController.changeScene(event, "Register.fxml", "Sign Up", null);
    }
    
//    public void switchToLoggedIn (ActionEvent e) throws IOException{
//        Parent root = FXMLLoader.load(getClass().getResource("LoggedIn.fxml"));
//        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//    
//    public void switchToRegister (ActionEvent e) throws IOException{
//        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
//        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
}
