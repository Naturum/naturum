/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package XP_Leaderboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.sql.DriverManager;
import java.sql.Connection;
import java.util.List;
import java.sql.SQLException;
import naturum.User;

/**
 *
 * @author koayk
 */
public class XpLeaderboardMain extends Application {
    
    //JDBC URL, username, and password are the connection parameters that are used to access the database
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/naturum";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Handsome039*";
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("XpLeaderboard.fxml"));
        Parent root = loader.load();
        
        XpLeaderboardController controller = loader.getController();
        //establish connection to database
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            List<User> users = controller.getUsernameXP(connection);
            controller.displayLeaderboard(users);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        Scene scene = new Scene(root);
        stage.setTitle("NaturUM");
        Image icon = new Image(getClass().getResourceAsStream("naturum-logo.png"));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
    }
}
