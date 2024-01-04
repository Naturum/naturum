/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trivia;

/**
 *
 * @author Ryan Chin
 */
import Login.User;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    
    private RealTrivia trivia;
    
    private String correct;
    
    //Method to display Trivia question based on the object attributes
    public void displayQuestion(User u, boolean replay, int day) throws IOException{
        this.trivia = new RealTrivia(u, replay, day);
        String[] options = trivia.getOptions();
        question.setText(trivia.getQuestion());
        answer1.setText(options[0]);
        answer2.setText(options[1]);
        answer3.setText(options[2]);
        answer4.setText(options[3]);
    }
    
    
    //Method that is called when user submits an answer
    public void AnswerMechanism(ActionEvent event){
        Object source = event.getSource();
        Button answerButton = (Button)source;
        String answer = answerButton.getText(); //The text of the button that user clicked is the submitted answer
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        
        //If answer is wrong 
        if (!trivia.answered(answer)) {
            if (trivia.getAttempts() <0){
                correctAnswer.setText(trivia.getAnswer());
                deathScreen.setVisible(true);
            }else
                WrongOption();
        //If answer is correct
        }else{
            correctScreen.setVisible(true);
            if (trivia.getReplay()){
                pointsMessage.setText("This is a replay, no points are awarded.");
            } else{
                if (trivia.getAttempts()==1)
                    pointsMessage.setText("You have been awarded 2 points, you now have " + u.getPoints() + " points.");
                else{
                    pointsMessage.setText("You have been awarded 1 point, you now have " + u.getPoints() + " points.");
                }
            }
        }
    }
    
    //Called when answer is wrong but user still has attempts
    public void WrongOption(){
       wrongScreen.setVisible(true); //show wrong screen
       //shuffle the options
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
       
       //Loading animation for the user to think
       Timeline timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(cooldown.progressProperty(), 0)),
        new KeyFrame(Duration.seconds(3), e-> {
            wrongScreen.setVisible(false);
        }, new KeyValue(cooldown.progressProperty(), 1))    
    );
    timeline.setCycleCount(1);
    timeline.play();
    }
    
    //Switch to triviaMenu.fxml
    public void switchToTriviaMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/triviaMenu.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        
        //Get current day and pass to initialise next page
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
