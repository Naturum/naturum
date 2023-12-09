/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naturum;

import java.sql.Connection;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    int day;
    
    public void switchToLogin(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTriviaMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("triviaMenu.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        LocalDate now = LocalDate.now();
        day = (int) ChronoUnit.DAYS.between(u.getRegDate(), now) + 1;
        Connection con = SQLController.getConnection();
        triviaMenuController triviaMenuCon = loader.getController();
        triviaMenuCon.init(SQLController.checkDailyTrivia(con, u.getIdUser(), day), day);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
