/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naturum;

import com.sun.jdi.connect.spi.Connection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Ryan Chin
 */
public class triviaReplayController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML Label question, correctAnswer, dayTitle;
    @FXML Button answer1, answer2, answer3, answer4, home;
    @FXML AnchorPane correctScreen, wrongScreen, deathScreen, answerButtons;
    @FXML ProgressBar cooldown, correctCD;
    @FXML CheckBox answerToggle;
    
    private int day;
    private final Random g = new Random();
    private String[] answerText;
    private ArrayList<Integer> days = new ArrayList<>();
    
    private String correct;
    public void initQuestion(int day, int iduser) throws IOException{
        java.sql.Connection con = SQLController.getConnection();
        days = SQLController.getReplayRecord(con, iduser);
        displayQuestion(day);
    }
    
    public void displayQuestion(int day) throws IOException{
        dayTitle.setText("Day "+day);
        this.day = day;
        String[] trivia = Trivia.getTrivia(day);
        question.setText(trivia[0]);
        answer1.setText(trivia[1]);
        answer2.setText(trivia[2]);
        answer3.setText(trivia[3]);
        answer4.setText(trivia[4]);
        correct = trivia[5];
    }
    
    public void AnswerMechanism(ActionEvent event){
        Object source = event.getSource();
        Button answerButton = (Button)source;
        String answer = answerButton.getText();
        if (!answer.equals(correct))
            WrongOption();
        else
            CorrectOption();
    }
    
    public void showAnswers(ActionEvent event){
        if(answerToggle.isSelected()){
            for (Node node : answerButtons.getChildren()){
                if (((Button)node).getText().equals(correct))
                    ((Button)node).setStyle("-fx-background-color: #3acf61");
                ((Button)node).setDisable(true);
            }
        } else{
            for (Node node : answerButtons.getChildren()){
                ((Button)node).setDisable(false);
                ((Button)node).setStyle(null);
            }
        }
    }
    
    public void nextQuestion(ActionEvent event) throws IOException{
        for (int i=day+1; i<=11; i++){
            if (days.contains(i)){
                displayQuestion(i);
                break;
            }else if (i==11)
                i=0;
        }
    }
    
    public void previousQuestion(ActionEvent event) throws IOException{
        for (int i= day-1; i>=0; i--){
            if (days.contains(i)){
                displayQuestion(i);
                break;
            }else if (i==0)
                i=10;
        }
        
        
    }
    
    public void WrongOption(){
       wrongScreen.setVisible(true);
       int[] xcoord = {70,355,70,355};
       int[] ycoord = {49,49,174,174};
       int[] indices = {0,1,2,3};
       for(int i = 0; i<4;i++){
           int randomIndex = g.nextInt(4);
           int temp = indices[randomIndex];
           indices[randomIndex] = indices[i];
           indices[i] = temp;
       }
       answer1.setLayoutX(xcoord[indices[0]]);
       answer1.setLayoutY(ycoord[indices[0]]);
       answer2.setLayoutX(xcoord[indices[1]]);
       answer2.setLayoutY(ycoord[indices[1]]);
       answer3.setLayoutX(xcoord[indices[2]]);
       answer3.setLayoutY(ycoord[indices[2]]);
       answer4.setLayoutX(xcoord[indices[3]]);
       answer4.setLayoutY(ycoord[indices[3]]);
       
       Timeline timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(cooldown.progressProperty(), 0)),
        new KeyFrame(Duration.seconds(3), e-> {
            wrongScreen.setVisible(false);
        }, new KeyValue(cooldown.progressProperty(), 1))    
    );
    timeline.setCycleCount(1);
    timeline.play();
    }
    public void CorrectOption(){
        correctScreen.setVisible(true);
        Timeline timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(correctCD.progressProperty(), 0)),
        new KeyFrame(Duration.seconds(3), e-> {
            correctScreen.setVisible(false);
        }, new KeyValue(correctCD.progressProperty(), 1))    
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    
    public void switchToTriviaMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("triviaMenu.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    
}
