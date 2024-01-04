/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Donation_;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Login.User;



/**
 *
 * @author koayk
 */
public class DonationController implements Initializable{
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int amountTotal;
    
    @FXML
    ChoiceBox<String> ngoChoiceBox;
    
    @FXML
    ChoiceBox<String> paymentChoiceBox;
    
    @FXML
    Label reminderLabel;
    
    @FXML
    Label donationLabel;
    
    @FXML 
    Label pointLabel; 
    
    @FXML
    TextField amountTextField;
    
    String[] ngo = {"WWF","Fauna & Flora International","Malaysia Nature Society"};
    String[] paymentMethods = {"TnG eWallet","Credit card","FPX"};
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ngoChoiceBox.getItems().addAll(ngo);
        paymentChoiceBox.getItems().addAll(paymentMethods);
    }
    
    public void back(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXMLfiles/Donation_MainPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void confirm(ActionEvent e) throws IOException {
        if (ngoChoiceBox.getValue() == null) {
            reminderLabel.setText("Please select NGO to donate !");
            return;
        }
        try {
            amountTotal = Integer.parseInt(amountTextField.getText());
            System.out.println("User has entered " + amountTotal + " in the textfield provided");
        }
        catch (NumberFormatException Event) {
            amountTextField.clear();
            reminderLabel.setText("Please make sure your donation amount is integer only");
            return;
        }        
        if (paymentChoiceBox.getValue() == null) {
            reminderLabel.setText("Please select your payment method !");
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/Donation_paymentStatement.fxml"));
            root = loader.load();
            
            DonationController2 controller = loader.getController();
            int point = amountTotal * 10;
            controller.displayDonation(amountTotal);
            controller.displayPoint(point);
            
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            User user = (User) stage.getUserData();
            user.addPoints(point);
            user.addXP(point);
            user.setXpLastUpdate(LocalDateTime.now());
            
            String username = user.getUsername();
            System.out.println("The username of the user is " + username); // To check the username
            double donation = amountTotal * 0.9;
            String ngo = ngoChoiceBox.getValue();
            DonationWriter writer = new DonationWriter();
            writer.WriteToDonationFile(username, donation, ngo);
            
            System.out.println("The donation amount is $" + amountTotal); // Check the donation amount
            System.out.println("The points awarded is " + point); // Check the point awarded 
            
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
} 

