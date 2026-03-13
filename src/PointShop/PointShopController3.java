/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PointShop;

import Login.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author koayk
 */
public class PointShopController3 {

    @FXML
    TextField nameTextField;

    @FXML
    Label nameLabel;
    
    @FXML
    TextField treePlantingPoint;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void confirm(ActionEvent e) throws Exception {
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        User user = (User) stage.getUserData();
        String username = user.getUsername();
        int userPoints = user.getPoints();
        System.out.println("Current points of " + username + " are " + userPoints); // To track the current points and username of user
       
        if (nameTextField.getText().equals("")) {
            nameLabel.setText("Please enter any name to be associated to the tree !");
        }
        else if (userPoints >= 1000) {
            user.deductPoints(1000);
            System.out.println("Points of " + username + " after ordering are " + user.getPoints()); // To track the points after user has ordered
            
            PointShopWriter writer = new PointShopWriter();
            writer.WriteToTreePlantOrder(username, nameTextField.getText());
            
            root = FXMLLoader.load(getClass().getResource("/FXMLfiles/PointShop_order_successful.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();         
        }
        else {
            nameLabel.setText("Your current points are not enough to plant a tree");
            nameTextField.clear();
        }
    }

    public void back(ActionEvent e) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/FXMLfiles/PointShop_page.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void displayPoint(int points) {
        treePlantingPoint.setText("Points: " + points);
    }
    
}
