/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Donation_;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author koayk
 */
public class DonationController2 {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    Label donationLabel;
    
    @FXML 
    Label pointLabel;

    public void displayDonation(int amount) { 
        donationLabel.setText("You have donated $" + amount);
    }
    public void displayPoint(int points) {
        pointLabel.setText(points + " points are rewarded");
    }
    
    public void next(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXMLfiles/Donation_transactionAndNGO.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setX(500);
        stage.setY(50);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();      
    }
    
    public void switchToMain(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/LoggedIn.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
