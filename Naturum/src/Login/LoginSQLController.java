/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

/**
 *
 * @author johnong04
 */
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;



public class LoginSQLController {
    public void logInUser(ActionEvent event, String email, String password){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/naturum", "root", "Handsome039*");
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
                    byte[] salt = rs.getBytes("saltString");
                    byte[] hash = rs.getBytes("password");    
                    Hasher hasher = new Hasher(salt, password.toCharArray());
                    byte[] testPassword = hasher.getHash();
                    
                    if (Arrays.equals(testPassword, hash)){
                        ps = con.prepareStatement("SELECT username FROM users WHERE email = ?");
                        ps.setString(1, email);
                        rs = ps.executeQuery();
                        rs.next();
                        String username = rs.getString("username");
                        SceneManager.changeScene(event, "/FXMLfiles/LoggedIn.fxml", "Main Page", username,null, userCreation(email));
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/naturum", "root", "Handsome039*");
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
                Hasher hasher = new Hasher(password.toCharArray());
                byte[] salt = hasher.getSalt();
                byte[] hash = hasher.getHash();
                
                psInsert = con.prepareStatement("INSERT INTO users (username, password, saltString, email, regDate, logInDate, point, xp, xpLastUpdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, now())");
                psInsert.setString(1, username);
                psInsert.setBytes(2, hash);
                psInsert.setBytes(3, salt);
                psInsert.setString(4, email);
                psInsert.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                psInsert.setDate(6, new java.sql.Date(System.currentTimeMillis()));
                psInsert.setInt(7, 0);
                psInsert.setInt(8, 0);
                psInsert.executeUpdate();
           
                SceneManager.changeScene(event, "/FXMLfiles/loggedIn.fxml", "Main Page", username,null, userCreation(email));
            }
        } catch (SQLException e){
            e.printStackTrace();
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
    
    public User userCreation(String email){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/naturum", "root", "Handsome039*");
            ps = con.prepareStatement("SELECT * FROM users WHERE BINARY email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            while(rs.next()){
                int iduser = rs.getInt("userid");
                String username = rs.getString("username");
                byte[] password = rs.getBytes("password");
                LocalDate regDate = (rs.getDate("regDate")).toLocalDate();
                LocalDate lastLoginDate = (rs.getDate("logInDate")).toLocalDate();
                int point = rs.getInt("point");
                int xp = rs.getInt("xp");
                LocalDateTime xpLastUpdated = (rs.getTimestamp("xpLastUpdate")).toLocalDateTime();
                User u = new User(iduser, email, password, point, lastLoginDate, regDate, username, xp, xpLastUpdated);
                return u;
            }
        } catch (SQLException e){
            e.printStackTrace();
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
        return null;
    }
    
    public void updateUser(User u){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/naturum", "root", "Handsome039*");
            ps = con.prepareStatement("UPDATE users SET logInDate = ?, point = ?, xp = ?, xpLastUpdate = ? WHERE userid = ?");
            ps.setDate(1, java.sql.Date.valueOf(u.getLastLoginDate()));
            ps.setInt(2, u.getPoints());
            ps.setInt(3, u.getXP());
            ps.setTimestamp(4, Timestamp.valueOf(u.getXpLastUpdate()));
            ps.setInt(5, u.getIdUser());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
