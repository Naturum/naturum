/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Login;

import XP_Leaderboard.XpLeaderboardController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import news.NewsController;
import trivia.SQLController;
import trivia.triviaMenuController;

/**
 * FXML Controller class
 *
 * @author johnong04
 */
public class LoggedInController implements Initializable {

@FXML
public Button button_logout;
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
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                User u = (User) stage.getUserData();
                if (u!=null){
                    LoginSQLController sc = new LoginSQLController();
                    sc.updateUser(u);
                }
                SceneManager.changeScene(event, "/FXMLfiles/LogIn.fxml", "Log In!", null, button_logout, null);
            }
            
        });
    }    
    
    public void switchToTriviaMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/triviaMenu.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        LocalDate now = LocalDate.now();
        int day = (int) ChronoUnit.DAYS.between(u.getRegDate(), now) + 1;
        SQLController sc = new SQLController();
        sc.openConnection();
        triviaMenuController triviaMenuCon = loader.getController();
        triviaMenuCon.init(sc.checkDailyTrivia(u.getIdUser(), day), day);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToDonation(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/Donation_MainPage.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToCheckIn(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/DailyCheckIn_main.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToPointShop(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/PointShop_page.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToXpLeaderboard(ActionEvent event) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/XpLeaderboard.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        XpLeaderboardController controller = loader.getController();
        controller.displayLeaderboard((User) stage.getUserData());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToNews(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/News.fxml"));
        root = loader.load();
        NewsController controller = loader.getController();
        controller.init();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setUserInfo (String username){
        label_welcome.setText("Welcome " + username + "!" );
    }
}

