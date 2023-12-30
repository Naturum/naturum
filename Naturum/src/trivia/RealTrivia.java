/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trivia;

import Login.User;

/**
 *
 * @author Ryan Chin
 */
public class RealTrivia extends Trivia{
    private int attempts;
    private boolean replay;
    private User u;
    
    public RealTrivia(User u, boolean replay, int day){
        super(day);
        this.u = u;
        this.attempts = 1;
        this.replay = replay;
    }
    
    public int getAttempts(){
        return attempts;
    }
    
    public boolean getReplay(){
        return replay;
    }
    
    public boolean answered(String answer){
        SQLController sc = new SQLController();
        if (super.checkAnswer(answer)){
            if (!replay){
                int points = (attempts==0) ? 1 : 2;
                u.addPoints(points);
                u.addXP(points);
            }
            sc.openConnection();
            sc.insertRecord(u.getIdUser(), super.getDay());
            return true;
        } else{
            if (this.attempts==0){
                sc.insertRecord(u.getIdUser(), super.getDay());
            } else{
                attempts-=1;
            }
            return false;
        }
    }
}
