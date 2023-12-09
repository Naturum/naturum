/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naturum;

import java.sql.*;

/**
 *
 * @author Ryan Chin
 */
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
public class SQLController {
    private static Connection con;
    public static Connection getConnection(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/naturum", "root", "naturum");//Establishing connection
            System.out.println("Connected With the database successfully"); //Message after successful connection
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database"); //Message if something goes wrong while conneting to the database
        }
        return con;
    }
    
    public static ArrayList getTriviaRecord(Connection con, int iduser){
        ArrayList<Integer> trivia = new ArrayList<>();
        try{ 
            PreparedStatement stmt = con.prepareStatement("select trivia from trivia where iduser=?");
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
    
    public static ArrayList getReplayRecord(Connection con, int iduser){
        ArrayList<Integer> days = new ArrayList<>();
        try{
            PreparedStatement stmt = con.prepareStatement("select trivia from trivia where iduser=? and status=1");
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
    
    public static void insertRecord(Connection con, int iduser, int day, int status){
        try{
            PreparedStatement stmt = con.prepareStatement("insert into trivia(idUser, trivia, status) VALUES (?, ?, ?)");
            stmt.setInt(1,iduser);
            stmt.setInt(2,day);
            stmt.setInt(3, status);
            
            stmt.executeUpdate();
            System.out.println("Inserted data");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        }
    }
    public static void updateRecord(Connection con, int iduser, int day){
        try{
            PreparedStatement stmt = con.prepareStatement("update trivia set status=1 where idUser=? and trivia=?");
            stmt.setInt(1,iduser);
            stmt.setInt(2,day);
            
            stmt.executeUpdate();
            
            con.close();
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        }
    }
    
    public static boolean checkDailyTrivia(Connection con, int iduser, int day){
        boolean check=false;
        try{
            PreparedStatement stmt = con.prepareStatement("select idtrivia from trivia where idUser=? and trivia=?");
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
