/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package XP_Leaderboard;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import Login.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author koayk
 */
public class XpLeaderboardController {
    
    @FXML
    TextArea textArea;
    
    @FXML
    TextArea textArea2;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void displayLeaderboard() throws SQLException {
        XpLeaderboard leaderboard = new XpLeaderboard();
        leaderboard.openConnection();
        List<User> users = leaderboard.getUsernameXP();
        StringBuilder formattedText = new StringBuilder();
        StringBuilder formattedText2 = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            formattedText.append(String.format("%d. %s\n", i+1, users.get(i).getUsername()));
            formattedText2.append(String.format("%25d\n", users.get(i).getXP()));
        }
        textArea.setText(formattedText.toString());
        textArea2.setText(formattedText2.toString());
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
