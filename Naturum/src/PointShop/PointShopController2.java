/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PointShop;

import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import naturum.User;

/**
 *
 * @author koayk
 */
public class PointShopController2 implements Initializable {
    
    @FXML
    Label noticeLabel;
            
    @FXML
    ChoiceBox<String> merchChoiceBox;
    String[] merch = {"merch-1","merch-2","merch-3"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        merchChoiceBox.getItems().addAll(merch);
    }
    
    @FXML 
    TextField purchaseTextField;
    
    @FXML
    TextArea addressTextArea;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void backButton(ActionEvent e) throws Exception {
        root = FXMLLoader.load(getClass().getResource("PointShop_page.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void confirmButton(ActionEvent e) throws Exception {
        
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        User user = (User) stage.getUserData();
        String username = user.getUsername();
        int userPoints = user.getPoints();
        System.out.println("Current points of " + username + " are " + userPoints); // To track the username and current points of the user 
        
        if (merchChoiceBox.getValue() == null) {
            noticeLabel.setText("Please select the merchandise to redeem !");
        }
        else if (!(isInteger(purchaseTextField.getText()))) {
            noticeLabel.setText("Please select the number of merchandise to be purchased !");
            purchaseTextField.clear();
        }
        else if (addressTextArea.getText().equals("")) {
            noticeLabel.setText("Please state the delivery address !");
        }
        else if (userPoints >= pointsNeedToRedeem(merchChoiceBox.getValue(),Integer.parseInt(purchaseTextField.getText()))){
            user.deductPoints(pointsNeedToRedeem(merchChoiceBox.getValue(),Integer.parseInt(purchaseTextField.getText())));
            System.out.println("Points of " + username + " after ordering are " + user.getPoints()); // To track the points of user after redeeming the merchandise
            
            PointShopWriter writer = new PointShopWriter();
            writer.WriteToMerchandiseOrder(username, Integer.parseInt(purchaseTextField.getText()), merchChoiceBox.getValue(), addressTextArea.getText());
            
            root = FXMLLoader.load(getClass().getResource("PointShop_order_successful.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            noticeLabel.setText("Your current points are not enough to redeem the items");
            purchaseTextField.clear();
        }
    }
    
    public static boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
        }
        catch (NumberFormatException event) {
            return false;
        }
        return true;
    } 
    
    public static int pointsNeedToRedeem(String merchandise, int purchaseNum) {
        int totalPoints = 0;
        if (merchandise.equals("merch-1")) {
            totalPoints = purchaseNum * 100;
        }
        else if (merchandise.equals("merch-2")) {
            totalPoints = purchaseNum * 300;
        }
        else if (merchandise.equals("merch-3")) {
            totalPoints = purchaseNum * 500;
        }
        return totalPoints;
    }
}
            
            
            
             