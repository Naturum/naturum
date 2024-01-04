/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daily_Check_In;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.scene.Node;
import Login.User;
import java.io.IOException;

/**
 *
 * @author koayk
 */
public class DailyCheckInController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void checkIn(ActionEvent e) throws Exception {
        
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        LocalDate today = LocalDate.now();
        
        // Two lines below just to track the points and lastlogindate of the user object
        System.out.println("Point before check in : " + u.getPoints());
        System.out.println("Last login date is " + u.getLastLoginDate());
        
        if (!(u.getLastLoginDate()).isEqual(today)) {
            u.setLastLoginDate(today);
            u.addPoints(1);
            u.addXP(1);
            u.setXpLastUpdate(LocalDateTime.now());
            
            // Two lines below just to track the points and lastlogindate of the user object
            System.out.println("Point after check in : " + u.getPoints());
            System.out.println("The updated last login date is " + u.getLastLoginDate());
            
            root = FXMLLoader.load(getClass().getResource("/FXMLfiles/Successful_check_in.fxml")); 
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); 
        }
        else {
            root = FXMLLoader.load(getClass().getResource("/FXMLfiles/Fail_check_in.fxml")); 
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();            
        }
    }
    
    public void switchToMain(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/LoggedIn.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
