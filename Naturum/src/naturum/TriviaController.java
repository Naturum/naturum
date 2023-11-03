/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naturum;

/**
 *
 * @author Ryan Chin
 */
import java.io.BufferedReader;
import java.io.FileReader;
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
    
    @FXML Label question, correctAnswer;
    @FXML Button answer1, answer2, answer3, answer4, home;
    @FXML AnchorPane correctScreen, wrongScreen, deathScreen;
    @FXML ProgressBar cooldown;
    
    private final Random g = new Random();
    private String[] answerText;
    
    private int attempts = 1;
    private int day = 1;
    
    private String correct;
    public void displayQuestion() throws IOException{
        String questionTitle;
        try(BufferedReader br = new BufferedReader(new FileReader("./TriviaSample.txt"))){
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            for (int i =0; i<(day*4);i++)
                br.readLine();
            sb.append(br.readLine());
            questionTitle = sb.toString();
            sb2.append(br.readLine());
            answerText = sb2.toString().split(",");
            sb3.append(br.readLine());
            correct = sb3.toString();
        }
        question.setText(questionTitle);
        answer1.setText(answerText[0]);
        answer2.setText(answerText[1]);
        answer3.setText(answerText[2]);
        answer4.setText(answerText[3]);
    }
    
    public void AnswerMechanism(ActionEvent event){
        Object source = event.getSource();
        Button answerButton = (Button)source;
        String answer = answerButton.getText();
        if (!answer.equals(correct))
            if (attempts ==0){
                correctAnswer.setText(correct);
                deathScreen.setVisible(true);
            }else
                WrongOption();
        else
            CorrectOption();
    }
    
    public void WrongOption(){
       attempts -= 1;
       wrongScreen.setVisible(true);
       int[] xcoord = {70,362,70,362};
       int[] ycoord = {162,162,290,290};
       int[] indices = {0,1,2,3};
       for(int i = 0; i<answerText.length;i++){
           int randomIndex = g.nextInt(answerText.length);
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
    }
    
    public void switchToMainpage(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    
}
