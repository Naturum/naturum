/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author Ryan Chin
 */
public class User {
    private int iduser;
    private String email;
    private byte[] password;
    private int points;
    private LocalDate lastLoginDate;
    private LocalDate regDate;
    private String username;
    private int xp;
    private LocalDateTime xpLastUpdate;
    
    public User(int iduser, String email, byte[] pw, int points, LocalDate loginDate, LocalDate regDate, String username, int xp, LocalDateTime xpLastUpdate) {
        this.iduser = iduser;
        this. email = email;
        this.password = pw;
        this.points = points;
        this.lastLoginDate = loginDate;
        this.regDate = regDate;
        this.username = username;
        this.xp = xp;
        this.xpLastUpdate = xpLastUpdate;
    }
    
    public User(String username, int xp, LocalDateTime xpLastUpdate) {
        this.username = username;
        this.xp = xp;
        this.xpLastUpdate = xpLastUpdate;
    }
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
    
    public void deductPoints(int newPoints) {
        points -= newPoints;
    }
        
    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }
    
    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    
    public void setUsername(String username) { 
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void addXP(int points) {
        this.xp += points;
    }
    
    public int getXP() {
        return xp;
    }
    
    public LocalDateTime getXpLastUpdate() {
        return xpLastUpdate;
    }
    
    public void setXpLastUpdate(LocalDateTime xpLastUpdate) {
        this.xpLastUpdate = xpLastUpdate;
    }
    
    public void setRegDate(LocalDate date){
        this.regDate = date;
    }
    
    public LocalDate getRegDate(){
        return regDate;
    }
}
