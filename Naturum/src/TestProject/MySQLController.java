/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestProject;

/**
 *
 * @author johnong04
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import static java.util.Objects.hash;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
//import javafx.util.Duration;



public class MySQLController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
//    public static void main(String[] args) {
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
    
    public void changeScene(ActionEvent event, String fxmlfile, String title, String username, Button button){
        Parent root = null;
        
        if (username != null){
            try{
                FXMLLoader loader = new FXMLLoader(MySQLController.class.getResource(fxmlfile));
                root = loader.load();
                LoggedInController loggedincontroller = loader.getController();
                loggedincontroller.setUserInfo(username);
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setTitle(title);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            try{
                Parent root1 = FXMLLoader.load(MySQLController.class.getResource(fxmlfile));
                scene = button.getScene();
                if (fxmlfile.equals("Register.fxml")){
                    root1.translateXProperty().set(scene.getWidth());
                }
                else{
                    root1.translateXProperty().set(-scene.getWidth());
                }

                Node oldRoot = scene.getRoot();
                StackPane stackPane = new StackPane(oldRoot, root1);
                scene.setRoot(stackPane);
//                scene.setRoot(root1);
                
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root1.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(0.5),kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1 -> {
                    stackPane.getChildren().remove(oldRoot);
//                    ((Group) scene.getRoot()).getChildren().remove(oldRoot);
                });
                timeline.play();
                
            } catch (IOException e){
                e.printStackTrace();
            }
        }
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.setTitle(title);
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }
    
    public void logInUser(ActionEvent event, String email, String password){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb", "root", "Titor@MySQL2");
            ps = con.prepareStatement("SELECT password, saltString FROM users WHERE BINARY email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            if (!rs.isBeforeFirst()){
                System.out.println("User not found!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User does not exist. Please try again or sign up!");
                alert.show();
            }
            else{
                while(rs.next()){
//                    String salt1 = rs.getString("saltString");
//                    byte[] salt = Base64.getDecoder().decode(salt1);
                    byte[] salt = rs.getBytes("saltString");
                    byte[] hash = rs.getBytes("password");
//                      InputStream hashStream = rs.getBinaryStream("password");
//                      byte[] hash = new byte[64];
//                      hashStream.read(hash);
//                      InputStream saltStream = rs.getBinaryStream("saltString");
//                      byte[] salt = new byte[16];
//                      hashStream.read(salt);
//                    String storedPassword1 = rs.getString("password");
//                    byte[] storedPassword = Base64.getDecoder().decode(storedPassword1);
//                    String storePassword = Base64.getEncoder().encodeToString(storedPassword);
//                    StringBuilder sb = new StringBuilder();
//                    for (byte b : storedPassword) {
//                        sb.append(String.format("%02x", b));
//                    }
//                    String storePassword = sb.toString();
                    
                    int iterations = 100000;
                    int keyLength = 256;
                    String algorithm = "PBKDF2WithHmacSHA256";
                    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
                    SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
                    byte[] testPassword = factory.generateSecret(spec).getEncoded();
//                    String hashedPassword = Base64.getEncoder().encodeToString(hashPassword);
//                    for (byte b : hashPassword) {
//                        sb.append(String.format("%02x", b));
//                    }
//                    String hashedPassword = sb.toString();

                        

                    if (Arrays.equals(testPassword, hash)){
                        ps = con.prepareStatement("SELECT username FROM users WHERE email = ?");
                        ps.setString(1, email);
                        rs = ps.executeQuery();
                        rs.next();
                        String username = rs.getString("username");
                        changeScene(event, "loggedIn.fxml", "Main Page", username,null);
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
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null){
                try{
                    rs.close();
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
    
    public void signUpUser(ActionEvent event, String email, String username, String password, String password1){
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
            
            String regex = "^[a-zA-Z0-9_.-]+@(gmail\\.com|yahoo\\.com|hotmail\\.com|outlook\\.com)$";
            Pattern pattern = Pattern.compile (regex);
            Matcher matcher = pattern.matcher (email);
            
            if (!password.equals(password1)){
                System.out.println("Password do not match!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Password do not match! Try again.");
                alert.show();
            }
            
            else if (resultSet.isBeforeFirst()){
                System.out.println("User already exist!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username taken! Pick another username.");
                alert.show();
            }
            
            else if (!matcher.matches()) {  
                System.out.println("Email is not valid!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid email! Enter a valid email.");
                alert.show();
            } 
            
            else{
                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[16];
                random.nextBytes(salt);
//                InputStream saltStream = new ByteArrayInputStream(salt);
                int iterations = 100000;
                int keyLength = 256;
                String algorithm = "PBKDF2WithHmacSHA256";
                PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
                SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
                byte[] hash = factory.generateSecret(spec).getEncoded();
//                InputStream hashStream = new ByteArrayInputStream(hash);
//                String hexHash = Base64.getEncoder().encodeToString(hash);
//                String saltString = Base64.getEncoder().encodeToString(salt);
//                StringBuilder sb = new StringBuilder();
//                for (byte b : hash) {
//                sb.append(String.format("%02x", b));
//                }
//                String hexHash = sb.toString();
//                StringBuilder hexString = new StringBuilder();
//                for (byte b : salt) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//                }
//                String saltString = hexString.toString();
                
                psInsert = con.prepareStatement("INSERT INTO users (username, password, saltString, email, regDate, logInDate, point, xp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setBytes(2, hash);
                psInsert.setBytes(3, salt);
                psInsert.setString(4, email);
                psInsert.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                psInsert.setDate(6, new java.sql.Date(System.currentTimeMillis()));
                psInsert.setInt(7, 0);
                psInsert.setInt(8, 0);
                psInsert.executeUpdate();
           
                changeScene(event, "loggedIn.fxml", "Main Page", username,null);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MySQLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
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
