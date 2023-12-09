/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naturum;

/**
 *
 * @author Ryan Chin
 */
import java.time.LocalDate;
public class User {
    private int iduser;
    private String email;
    private String password;
    private int points;
    private LocalDate regDate;
    
    public int getIdUser(){
        return iduser;
    }
    
    public int getPoints(){
        return points;
    }
    
    public LocalDate getRegDate(){
        return regDate;
    }
    
    public void setIdUser(int idusernow){
        iduser = idusernow;
    }
    
    public void setRegDate(LocalDate regDate){
        this.regDate = regDate;
    }
    public void setPoints(int points){
        this.points = points;
    }
    
    public void addPoints(int newPoints){
        points += newPoints;
    }
    
}
