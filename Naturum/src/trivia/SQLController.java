/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trivia;

import java.sql.*;

/**
 *
 * @author Ryan Chin
 */
import java.util.*;

//Class to manage all databse operations
public class SQLController {
    private Connection con;
    
    //Establish connection with database
    public void openConnection(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/naturum", "root", "naturum");//Establishing connection
            System.out.println("Connected With the database successfully"); //Message after successful connection
        } catch (SQLException e){
            System.out.println("Error while connecting to the database"); //Message if something goes wrong while conneting to the database
        }
    }
    
    //retrieve list of completed trivias of the user (used in triviaRecordController & triviaReplayController)
    public ArrayList getTriviaRecord(int iduser){
        ArrayList<Integer> trivia = new ArrayList<>();
        try{ 
            PreparedStatement stmt = con.prepareStatement("select trivia from trivia where userid=?");
            stmt.setInt(1, iduser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                trivia.add(rs.getInt("trivia"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database"); 
        }
        return trivia;
    }
    
    //Record in database that user has completed this trivia (used in Trivia)
    public void insertRecord(int iduser, int day){
        try{
            PreparedStatement stmt = con.prepareStatement("insert into trivia(userid, trivia) VALUES (?, ?)");
            stmt.setInt(1,iduser);
            stmt.setInt(2,day);
            
            stmt.executeUpdate();
            System.out.println("Inserted data");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        }
    }
    
    //Check if user has done trivia today or not
    public boolean checkDailyTrivia(int iduser, int day){
        boolean check=false;
        if (day>10) //If day>10, there is no more trivias left. 
            return check;
        try{
            PreparedStatement stmt = con.prepareStatement("select idtrivia from trivia where userid=? and trivia=?");
            stmt.setInt(1, iduser);
            stmt.setInt(2, day);
            ResultSet rs = stmt.executeQuery();
            check = rs.isBeforeFirst();
            con.close();
            
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        }
        return check;
    }
    
}
