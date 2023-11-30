/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naturum;

/**
 *
 * @author Ryan Chin
 */
public class User {
    private int iduser;
    private String email;
    private String password;
    private int points;
    
    public int getIdUser(){
        return iduser;
    }
    
    public int getPoints(){
        return points;
    }
    
    public void setIdUser(int idusernow){
        iduser = idusernow;
    }
    
    public void setPoints(int points){
        this.points = points;
    }
    
    public void addPoints(int newPoints){
        points += newPoints;
    }
}
