/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trivia;

import Login.User;
import java.time.LocalDateTime;

/**
 *
 * @author Ryan Chin
 */
public class RealTrivia extends Trivia{
    
    //inherit from Trivia class
    private int attempts;
    private boolean replay; //check if the trivia is a replay or not. Replay trivias do not award points.
    private User u;
    
    //Constructor
    public RealTrivia(User u, boolean replay, int day){
        super(day);
        this.u = u;
        this.attempts = 1;
        this.replay = replay;
    }
    
    //Accessor methods
    public int getAttempts(){
        return attempts;
    }
    
    public boolean getReplay(){
        return replay;
    }
    
    //Algorithm when answer is submitted
    public boolean answered(String answer){
        SQLController sc = new SQLController();
        if (super.checkAnswer(answer)){ //Call superclass method to check if answer is correct or not
            if (!replay){ //If this trivia is not a replay, then award points
                int points = (attempts==0) ? 1 : 2; //points awarded based on remaining attempts
                u.addPoints(points);
                u.addXP(points);
                u.setXpLastUpdate(LocalDateTime.now());
            }
            sc.openConnection();
            sc.insertRecord(u.getIdUser(), super.getDay()); //Record in database that user has completed this trivia
            return true;
        } else{
            if (this.attempts==0){ //If user run out of attempts, record that user has completed trivia, no points awarded
                sc.insertRecord(u.getIdUser(), super.getDay());
            } else{ //otherwise, reduce attempts and let user keep going
                attempts-=1;
            }
            return false;
        }
    }
}
