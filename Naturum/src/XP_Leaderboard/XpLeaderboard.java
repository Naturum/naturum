/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package XP_Leaderboard;

import Login.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryan Chin
 */
public class XpLeaderboard {
    private Connection con;
        public void openConnection(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/naturum", "root", "naturum");//Establishing connection
            System.out.println("Connected With the database successfully"); //Message after successful connection
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database"); //Message if something goes wrong while conneting to the database
        }
    }
    //SQL query - retrieve username, xp and xpLastUpdate from database sorted by xp in descending order, if xp is tie for two or more user, then sorted with xpLastUpdate in ascending order
    private static final String GET_LEADERBOARD = "SELECT username, xp, xpLastUpdate FROM users ORDER BY xp DESC, xpLastUpdate ASC";
    /* 
    Statement below is a method that return List of User objects
    This method primarily is used to retrieve usename and xp from database
    To achieve that, parameter is set to Connection object which means the connection obj will be used to execute SQL queries
    */
    public List<User> getUsernameXP() throws SQLException {
        List<User> users = new ArrayList<>();
        /* 
        create a PreparedStatememt object and used to call the prepareStatement method on connection object
        "GET_LEADERBOARD" is sql query used to prepare statement
        PreparedStatement is an interface that is used to precompiled sql statements
        then, ResultSet object is created to call executeQuery method on preparedStatement obj 
        executeQuery is called to execute sql query and retrieve result set
        ResultSet represents the data retrieved from the database
        */
        try (PreparedStatement preparedStatement = con.prepareStatement(GET_LEADERBOARD); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) { //resultSet.next() is a boolean statement to check if there is nextLine
                //resultSet stores data in column and row, getString and getInt are the way to retrieve data from database
                String username = resultSet.getString("username");
                int xp = resultSet.getInt("xp");
                Object xpLastUpdateObject = resultSet.getObject("xpLastUpdate");
                //convert the retrieved object to LocalDateTime 
                LocalDateTime xpLastUpdate = null;
                if (xpLastUpdateObject instanceof java.sql.Timestamp) {
                    xpLastUpdate = ((java.sql.Timestamp) xpLastUpdateObject).toLocalDateTime();
                }
                users.add(new User(username,xp,xpLastUpdate)); //User user = new User(username,xp,xpLastUpdate), add user object that attached with data retrieved into array list
            }
        }
        return users;
    }  
}
    
