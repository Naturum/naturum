/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trivia;

import Login.User;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Ryan Chin
 */
public class triviaRecordController {
    @FXML AnchorPane buttonGroup;
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    int day;
    public void unlockRecord(User u){
        LocalDate now = LocalDate.now();
        int day = (int) ChronoUnit.DAYS.between(u.getRegDate(), now) + 1;
        SQLController sc = new SQLController();
        sc.openConnection();
        ArrayList<Integer> trivia = sc.getTriviaRecord(u.getIdUser());
        int i =1;
        for (Node node : buttonGroup.getChildren()){
            if (i<day){
                ((Button) node).setDisable(false);
                if (trivia.contains(i)){
                    ((Button) node).setStyle("-fx-background-color:  #FFFACD");
                    ((Button) node).setOnAction(e -> {try {
                        switchToReplay(e);
                    } catch (IOException ex) {System.out.println(ex);}
                });
                } else{
                    ((Button) node).setStyle("-fx-background-color:  #F5AB39");
                    ((Button) node).setOnAction(e -> {try {
                        switchToTrivia(e);
                    } catch (IOException ex) {System.out.println(ex);}
                        });
                    }
            } else if (i==day){
                if (trivia.contains(i)){
                    ((Button) node).setStyle("-fx-background-color:  #FFFACD");
                    ((Button) node).setDisable(false);
                    ((Button) node).setOnAction(e -> {try {
                        switchToReplay(e);
                    } catch (IOException ex) {System.out.println(ex);}
                });
                }
            }
            i++;
        }
    }
    
    public void switchToReplay(ActionEvent event) throws IOException{
        Object source = event.getSource();
        Button dayButton = (Button)source;
        int day = Character.getNumericValue(dayButton.getText().charAt(4));
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/triviaReplay.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();  
        User u = (User) stage.getUserData();
        triviaReplayController triviaReplayCon = loader.getController();
        triviaReplayCon.day = this.day;
        triviaReplayCon.initQuestion(day, u.getIdUser());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToTrivia(ActionEvent event) throws IOException{
        Object source = event.getSource();
        Button dayButton = (Button)source;
        int day = Character.getNumericValue(dayButton.getText().charAt(4));
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/trivia.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();  
        User u = (User) stage.getUserData();
        TriviaController triviaCon = loader.getController();
        triviaCon.displayQuestion(u, true, day);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        triviaMenuCon.init(sc.checkDailyTrivia(u.getIdUser(), day),day);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}