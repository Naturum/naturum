/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naturum;

import java.sql.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Ryan Chin
 */
public class Trivia {
    private static String[] trivia = new String[6];
    private static int day;
    
    public static String[] getTrivia(int day) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader("./TriviaSample.txt"))){
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            for (int i =0; i<((day-1)*4);i++)
                br.readLine();
            sb.append(br.readLine());
            trivia[0] = sb.toString();
            
            sb2.append(br.readLine());
            String[] answers = sb2.toString().split(",");
            int i=1;
            for (String answer: answers){
                trivia[i] = answer;
                i++;
            }
            sb3.append(br.readLine());
            trivia[5] = sb3.toString();
        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
        return trivia;
    }
    
    public static void completeTrivia(User user, int points, int day, boolean redo){
        Connection con = SQLController.getConnection();
        if (redo)
            SQLController.insertRecord(con, user.getIdUser(), day);
        else{
            user.addPoints(points);
            SQLController.insertRecord(con, user.getIdUser(), day);
        }
    }
    
    
}
