/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package naturum;   
import javafx.application.Application;  
//import javafx.event.ActionEvent;  
//import javafx.event.EventHandler;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;  
import javafx.stage.Stage;  
import javafx.scene.layout.GridPane;  
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Login_gui extends Application{  
  
    @Override  
    public void start(Stage primaryStage) throws Exception {  
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        Text sceneTitle = new Text("Login");
        sceneTitle.setTextAlignment(TextAlignment.CENTER);
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        grid.add(sceneTitle, 1, 1, 2, 1);
        
        Image image = new Image("assets/naturum-logo.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(66);
        grid.add(imageView, 1, 0, 2, 1);
        
        Label userName = new Label("Email:");
        grid.add(userName, 0, 3);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 3);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 4);

        PasswordField passwordBox = new PasswordField();
        grid.add(passwordBox, 1, 4);
        
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);
        
        Scene scene=new Scene(grid,600,400);    
        primaryStage.setTitle("NaturUM");  
        primaryStage.setScene(scene);  
        primaryStage.show();  
    }  
    public static void main (String[] args)  
    {  
        launch(args);  
    }  
  
}  
