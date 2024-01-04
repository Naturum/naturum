/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnong04
 */
public class RegisterController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    String name, pw;
    
    @FXML
    private Button button_signup;
    @FXML
    public Button button_login;
    @FXML 
    private TextField tf_email;
    @FXML 
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML 
    private TextField tf_password1;
    @FXML 
    private HBox hbox1;
    @FXML 
    public static AnchorPane signUpPane;
    @FXML 
    public static AnchorPane signUpPane1;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
     
    public void signUp1 (ActionEvent event){
        if (!tf_email.getText().trim().isEmpty() && !tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_password1.getText().trim().isEmpty()){
            LoginSQLController mysqlcontroller = new LoginSQLController();
            mysqlcontroller.signUpUser(event, tf_email.getText(), tf_username.getText(), tf_password.getText(),tf_password1.getText());
        }
        else {
            System.out.println("Please fill in all information!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all information to signup!");
            alert.show();
        }
    }
    public void logIn1(ActionEvent event){
        SceneManager.changeScene(event, "/FXMLfiles/LogIn.fxml", "Log in!", null, button_login, null);
    }
}
