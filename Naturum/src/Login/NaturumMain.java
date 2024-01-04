/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package Login;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author johnong04
 */
public class NaturumMain extends Application {
    
    private static Stage pstage;

    @Override
    public void start(Stage stage) throws IOException{
        try{
                NaturumMain.pstage = stage;
                Parent root = FXMLLoader.load(getClass().getResource("/FXMLfiles/LogIn.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Naturum");
                stage.getIcons().add(new Image("/assets/logo-icon.png"));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void stop(){
        User u = (User) pstage.getUserData();
        if (u!=null){
            LoginSQLController sc = new LoginSQLController();
            sc.updateUser(u);
        }
        System.out.println("Exiting");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

