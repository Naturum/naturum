/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PointShop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Login.User;
import java.io.IOException;

/**
 *
 * @author koayk
 */
public class PointShopController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void switchToMerchandise(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/PointShop_merchandise.fxml"));
        root = loader.load();
        PointShopController2 controller = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        User user = (User) stage.getUserData();
        int userPoints = user.getPoints();
        controller.displayPoint(userPoints);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToTreePlanting(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/PointShop_treePlanting.fxml"));
        root = loader.load();
        PointShopController3 controller2 = loader.getController();
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        User user = (User) stage.getUserData();
        int userPoints = user.getPoints();
        controller2.displayPoint(userPoints);
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
