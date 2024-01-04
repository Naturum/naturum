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
    
    public void switchToMain(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLfiles/LoggedIn.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void init(){
        NewsAPI news = new NewsAPI();
        String[][] articles = news.getArticles();
        int i = 0; 
        for (Node node : textGroup.getChildren()){
            ((Label) node).setText(articles[i][0] + "\n" + articles[i][2].substring(0,10));
            i++;
        }
        i = 0;
        for (Node node: linkGroup.getChildren()){
            ((Hyperlink) node).setText(articles[i][1]);
            i++;
        }
    }
    
    public void link(ActionEvent event) throws IOException{
        String url = ((Hyperlink) event.getSource()).getText();
        try{
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (Exception e){
            
        }
    }
    
    
}
