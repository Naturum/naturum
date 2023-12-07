/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestProject;

/**
 *
 * @author johnong04
 */
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MySQLController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
//    public void static main{
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "Titor@MySQL2");
//            System.out.println("Connection successful!");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static void changeScene(ActionEvent event, String fxmlfile, String title, String username){
        Parent root = null;
        
        if (username != null){
            try{
                FXMLLoader loader = new FXMLLoader(MySQLController.class.getResource(fxmlfile));
                root = loader.load();
                LoggedInController loggedincontroller = loader.getController();
                loggedincontroller.setUserInfo(username);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            try{
                root = FXMLLoader.load(MySQLController.class.getResource(fxmlfile));
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void logInUser(ActionEvent event, String username, String password){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "Titor@MySQL2");
            ps = con.prepareStatement("SELECT password FROM users WHERE username = ?");
            ps.setString(1, username);
            resultSet = ps.executeQuery();
            
            if (!resultSet.isBeforeFirst()){
                System.out.println("User not found!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username does not exist. Please try again or sign up!");
                alert.show();
            }
            else{
                while(resultSet.next()){
                    String testPassword = resultSet.getString("password");
                    if (testPassword.equals(password)){
                        changeScene(event, "loggedIn.fxml", "Main Page", username);
                    } else {
                        System.out.println("Password did not match.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please enter your password again!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (ps != null){
                try{
                    ps.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (con != null){
                try{
                    con.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void signUpUser(ActionEvent event, String username, String password){
        Connection con = null;
        PreparedStatement psCheckUserExist = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "Titor@MySQL2");
            psCheckUserExist = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExist.setString(1, username);
            resultSet = psCheckUserExist.executeQuery();
            
            if (resultSet.isBeforeFirst()){
                System.out.println("User already exist!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username taken! Pick another username.");
                alert.show();
            }
            else{
                psInsert = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();
           
                changeScene(event, "loggedIn.fxml", "Main Page", username);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resultSet != null){
                try{
                    resultSet.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExist != null){
                try{
                    psCheckUserExist.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null){
                try{
                    psInsert.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (con != null){
                try{
                    con.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
