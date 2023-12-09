/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package TestProject;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import static javafx.scene.text.Font.font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author johnong04
 */
public class NaturumMain extends Application {

    @Override
    public void start(Stage stage) throws IOException{
        try{
                Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}

//try{
//        Parent root = FXMLLoader.load(getClass().getResource("Testfxml.fxml"));
//        Group root = new Group();
//        Scene scene = new Scene(root,500,500,Color.WHEAT);
//        Text text = new Text("Login Page");
//        text.setX(15); text.setY(135);
//        text.setFill(Color.BLACK);
//        text.setFont(font("Garamond",40));
//        
//        Image icon = new Image("logo.png");
//        ImageView imageView = new ImageView(icon);
//        imageView.setX(0); imageView.setY(150);
//        imageView.setFitHeight(200); imageView.setFitWidth(200);
//        
//        root.getChildren().add(text);
//        root.getChildren().add(imageView);
//        stage.setTitle("NaturUM");
//        stage.setResizable(false);
//        stage.getIcons().add(icon);
//        
//        stage.setScene(scene);
//        stage.show();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
