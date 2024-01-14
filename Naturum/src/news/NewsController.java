/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package news;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.net.URL;

/**
 *
 * @author Ryan Chin
 */
public class NewsController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML AnchorPane textGroup, linkGroup;
    
    //Switch to LoggedIn.fxml
    public void switchToMain(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLfiles/LoggedIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    //Initialise the page
    public void init(){
        NewsAPI news = new NewsAPI(); //Create the object that connects to API
        String[][] articles = news.getArticles(); //Get list of news
        //Loop through the Label elements and set text as the news records retrieved
        int i = 0; 
        for (Node node : textGroup.getChildren()){
            ((Label) node).setText(articles[i][0] + "\n" + articles[i][2].substring(0,10)); //Title of news and date published
            i++;
        }
        //Loop through the Hyperlink elements to set the news article links
        i = 0;
        for (Node node: linkGroup.getChildren()){
            ((Hyperlink) node).setText(articles[i][1]);
            i++;
        }
    }
    
    //Method called when user clicks on news article's link
    public void link(ActionEvent event) throws IOException{
        String url = ((Hyperlink) event.getSource()).getText();
        try{
            Desktop.getDesktop().browse(new URL(url).toURI()); //Open the website on browser
        } catch (Exception e){
            
        }
    }
    
    
}
