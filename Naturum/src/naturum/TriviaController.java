/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naturum;

/**
 *
 * @author Ryan Chin
 */
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class TriviaController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML Label question, correctAnswer, pointsMessage;
    @FXML Button answer1, answer2, answer3, answer4, home;
    @FXML AnchorPane correctScreen, wrongScreen, deathScreen;
    @FXML ProgressBar cooldown;
    
    private final Random g = new Random();
    
    private int attempts = 1;
    private int day;
    private int points = 0;
    private boolean redo;
    
    private String correct;
    public void displayQuestion(int day, boolean redo) throws IOException{
        this.day = day;
        this.redo = redo;
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
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        if (!answer.equals(correct))
            if (attempts ==0){
                correctAnswer.setText(correct);
                deathScreen.setVisible(true);
                Trivia.completeTrivia(u, points, day, redo);
            }else
                WrongOption();
        else{
            points = (attempts==0) ? 1 : 2;
            Trivia.completeTrivia(u, points, day, redo);
            if (redo)
                pointsMessage.setText("(No points are awarded, as this is a replay)");
            else
                pointsMessage.setText("You have been awarded "+points+" points, you now have "+u.getPoints()+" points.");
            correctScreen.setVisible(true);
        }
    }
    
    public void WrongOption(){
       attempts -= 1;
       wrongScreen.setVisible(true);
       int[] xcoord = {70,362,70,362};
       int[] ycoord = {162,162,290,290};
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
    
    public void switchToMainpage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    
}
