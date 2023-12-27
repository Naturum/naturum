/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Daily_Check_In;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import naturum.User;

/**
 *
 * @author koayk
 */
public class DailyCheckIn extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        // Please remove these three lines of codes after compiling all files together, these lines of codes are used to test the functionality of daily check in system only.
        User user = new User();
        user.setLastLoginDate(LocalDate.of(2023, 12, 23)); // To check whether success or fail, please change the date accordingly
        stage.setUserData(user);
        
        Parent root = FXMLLoader.load(getClass().getResource("DailyCheckIn_main.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("NaturUM");
        Image icon = new Image(getClass().getResourceAsStream("naturum-logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
}


                