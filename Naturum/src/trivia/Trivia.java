/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trivia;

import Login.User;
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Ryan Chin
 */
public class Trivia {
    private String question;
    private String[] options;
    private String answer;
    private int day;
    
    public Trivia(int day){
        this.options = new String[4];
        this.day = day;
        try(BufferedReader br = new BufferedReader(new FileReader("./TriviaSample.txt"))){
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            for (int i =0; i<((day-1)*4);i++)
                br.readLine();
            sb.append(br.readLine());
            this.question = sb.toString();
            
            sb2.append(br.readLine());
            String[] answers = sb2.toString().split(",");
            int i=0;
            for (String answer: answers){
                options[i] = answer;
                i++;
            }
            sb3.append(br.readLine());
            this.answer = sb3.toString();
        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        } catch(IOException e){
            System.out.println("Problem with file input");
        }
    }
    
    public boolean checkAnswer(String answer){
        return (answer.equals(this.answer));
    }
    
    public static void completeTrivia(User user, int points, int day, boolean redo){
        SQLController sc = new SQLController();
        sc.openConnection();
        if (redo)
            sc.insertRecord(user.getIdUser(), day);
        else{
            user.addPoints(points);
            sc.insertRecord(user.getIdUser(), day);
        }
    }
    
    public String getQuestion(){
        return this.question;
    }
    
    public String[] getOptions(){
        return this.options;
    }
    
    public int getDay(){
        return this.day;
    }
}
