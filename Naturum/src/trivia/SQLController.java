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
public class SQLController {
    private Connection con;
    public void openConnection(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/naturum", "root", "Handsome039*");//Establishing connection
            System.out.println("Connected With the database successfully"); //Message after successful connection
        } catch (SQLException e){
            System.out.println("Error while connecting to the database"); //Message if something goes wrong while conneting to the database
        }
    }
    
    public ArrayList getTriviaRecord(int iduser){
        ArrayList<Integer> trivia = new ArrayList<>();
        try{ 
            PreparedStatement stmt = con.prepareStatement("select trivia from trivia where userid=?");
            stmt.setInt(1, iduser);
            ResultSet rs = stmt.executeQuery();
            int i=0;
            while (rs.next()){
                trivia.add(rs.getInt("trivia"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database"); 
        }
        return trivia;
    }
    
    public ArrayList getReplayRecord(int iduser){
        ArrayList<Integer> days = new ArrayList<>();
        try{
            PreparedStatement stmt = con.prepareStatement("select trivia from trivia where userid=?");
            stmt.setInt(1, iduser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                days.add(rs.getInt("trivia"));
            }
        } catch (SQLException e){
            System.out.println("Error while connecting to the database");
        }
        return days;
    }
    
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
    
    public boolean checkDailyTrivia(int iduser, int day){
        boolean check=false;
        if (day>10)
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
