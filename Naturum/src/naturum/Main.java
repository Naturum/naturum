/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package naturum;   
import java.time.LocalDate;
import javafx.application.Application;   
import javafx.fxml.FXMLLoader;  
import javafx.stage.Stage;  
import javafx.scene.Parent;
import javafx.scene.Scene;
public class Main extends Application{  
  
    @Override  
    public void start(Stage primaryStage) throws Exception {  
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root, 600, 400);
        
        User user = new User();
        user.setIdUser(1);
        user.setRegDate(LocalDate.of(2023, 12, 22));
        primaryStage.setUserData(user);
        
        primaryStage.setTitle("NaturUM");  
        primaryStage.setScene(scene);  
        primaryStage.show();  
    }  
    public static void main (String[] args)  
    {  
        launch(args);  
    }  
  
}  
