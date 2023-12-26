/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Donation_;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import naturum.User;
/**
 *
 * @author koayk
 */
public class DonationMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        // Please remove these three lines of codes after compiling all files together, these lines of codes are used to test the functionality of daily check in system only.
        User user = new User();
        user.setUsername("Noobmaster68"); // To test the function of the code, please manually change the username
        stage.setUserData(user);
        
        Parent root = FXMLLoader.load(getClass().getResource("Donation_MainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("NaturUM");
        Image icon = new Image(getClass().getResourceAsStream("naturum-logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
}
