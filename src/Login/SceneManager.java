/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import java.io.IOException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Ryan Chin
 */
public class SceneManager {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    
    public static void changeScene(ActionEvent event, String fxmlfile, String title, String username, Button button, User u){
        Parent root = null;
        
        if (username != null){
            try{
                FXMLLoader loader = new FXMLLoader(LoginSQLController.class.getResource(fxmlfile));
                root = loader.load();
                LoggedInController loggedincontroller = loader.getController();
                loggedincontroller.setUserInfo(username);
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setTitle(title);
                stage.setUserData(u);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            try{
                Parent root1 = FXMLLoader.load(LoginSQLController.class.getResource(fxmlfile));
                scene = button.getScene();
                if (fxmlfile.equals("/FXMLfiles/Register.fxml")){
                    root1.translateXProperty().set(scene.getWidth());
                }
                else{
                    root1.translateXProperty().set(-scene.getWidth());
                }

                Node oldRoot = scene.getRoot();
                StackPane stackPane = new StackPane(oldRoot, root1);
                scene.setRoot(stackPane);
                
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root1.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(0.5),kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1 -> {
                    stackPane.getChildren().remove(oldRoot);
                });
                timeline.play();
                
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
