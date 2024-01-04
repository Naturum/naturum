/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trivia;

import Login.User;
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
    
    int day;
    private int recordDay;
    private final Random g = new Random();
    private ArrayList<Integer> days = new ArrayList<>();
    private Trivia replay;
    
    //Start with the trivia based on what user pressed
    public void initQuestion(int day, int iduser) throws IOException{
        SQLController sc = new SQLController();
        sc.openConnection();
        days = sc.getTriviaRecord(iduser); //Get the list of completed trivias
        displayQuestion(day);
    }
    
    //Switch the trivia based on what is the next/previous day
    public void displayQuestion(int day) throws IOException{
        this.recordDay = day;
        dayTitle.setText("Day "+recordDay);
        //Display the trivia details
        replay = new Trivia(day);
        String[] options = replay.getOptions();
        question.setText(replay.getQuestion());
        answer1.setText(options[0]);
        answer2.setText(options[1]);
        answer3.setText(options[2]);
        answer4.setText(options[3]);
    }
    
    //Called when user submits an answer
    public void AnswerMechanism(ActionEvent event){
        Object source = event.getSource();
        Button answerButton = (Button)source;
        String answer = answerButton.getText();
        //Only need to check if wrong or right, as no points are involved
        if (!replay.checkAnswer(answer))
            WrongOption();
        else
            CorrectOption();
    }
    
    //Switch to trivia record
    public void switchToTriviaRecord(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/triviaRecord.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        User u = (User) stage.getUserData();
        triviaRecordController triviaRecordCon = loader.getController();
        triviaRecordCon.unlockRecord(u);

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    //Called when user want to see answers
    public void showAnswers(ActionEvent event){
        if(answerToggle.isSelected()){ //When user checks "Show Answers"
            for (Node node : answerButtons.getChildren()){
                //Check which button is right, and highlight it in green
                if (replay.checkAnswer(((Button)node).getText())) 
                    ((Button)node).setStyle("-fx-background-color: #3acf61");
                ((Button)node).setDisable(true);
            }
        } else{ //Switch the buttons back to default
            for (Node node : answerButtons.getChildren()){
                ((Button)node).setDisable(false);
                ((Button)node).setStyle("-fx-background-color:  #FFFACD");
            }
        }
    }
    
    //Called when the user want to see the next trivia
    public void nextQuestion(ActionEvent event) throws IOException{
        //Find the next trivia that user has completed
        for (int i=recordDay+1; i<=11; i++){
            if (days.contains(i)){
                displayQuestion(i);
                break;
            }else if (i==11) //if counter already at maximum trivia, reset back to 0 and continue loop
                i=0;
        }
    }
    
    //Called when the user want to see the previous trivia
    public void previousQuestion(ActionEvent event) throws IOException{
        for (int i= recordDay-1; i>=0; i--){
            //Find the previous trivia that user has completed
            if (days.contains(i)){
                displayQuestion(i);
                break;
            }else if (i==0) //if counter already at minimum trivia, reset back to 0 and continue loop
                i=10;
        }
        
        
    }
    
    public void WrongOption(){
        //shuffle the options
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
       
       //Loading animation to let the user think
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
        //Have a loading animation as cooldown
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
    
}
