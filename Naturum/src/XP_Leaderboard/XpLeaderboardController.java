/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package XP_Leaderboard;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import naturum.User;
/**
 *
 * @author koayk
 */
public class XpLeaderboardController {
    
    @FXML
    TextArea textArea;
    
    @FXML
    TextArea textArea2;
    
    //SQL query - retrieve username, xp and xpLastUpdate from database sorted by xp in descending order, if xp is tie for two or more user, then sorted with xpLastUpdate in ascending order
    private static final String GET_LEADERBOARD = "SELECT username, xp, xpLastUpdate FROM user ORDER BY xp DESC, xpLastUpdate ASC";
    /* 
    Statement below is a method that return List of User objects
    This method primarily is used to retrieve usename and xp from database
    To achieve that, parameter is set to Connection object which means the connection obj will be used to execute SQL queries
    */
    public List<User> getUsernameXP(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();
        /* 
        create a PreparedStatememt object and used to call the prepareStatement method on connection object
        "GET_LEADERBOARD" is sql query used to prepare statement
        PreparedStatement is an interface that is used to precompiled sql statements
        then, ResultSet object is created to call executeQuery method on preparedStatement obj 
        executeQuery is called to execute sql query and retrieve result set
        ResultSet represents the data retrieved from the database
        */
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LEADERBOARD); ResultSet resultSet = preparedStatement.executeQuery()) {
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
    
    public void displayLeaderboard(List<User> users) {
        StringBuilder formattedText = new StringBuilder();
        StringBuilder formattedText2 = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            formattedText.append(String.format("%d. %s\n", i+1, users.get(i).getUsername()));
            formattedText2.append(String.format("%25d\n", users.get(i).getXP()));
        }
        textArea.setText(formattedText.toString());
        textArea2.setText(formattedText2.toString());
    }                               
}
