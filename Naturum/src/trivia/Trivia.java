/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trivia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Ryan Chin
 */
public class Trivia {
    private String question;
    private String[] options;
    private String answer;
    private int day;
    
    //initialise trivia object
    public Trivia(int day){
        this.options = new String[4];
        this.day = day; //day of trivia to show
        
        //Read trivia details from text file
        try(BufferedReader br = new BufferedReader(new FileReader("./TriviaSample.txt"))){
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            
            //Pick which line to read for the specific trivia
            for (int i =0; i<((day-1)*4);i++) 
                br.readLine(); //skip the lines until the specific trivia part that we want
            
            //Read the trivia question from text file
            sb.append(br.readLine());
            this.question = sb.toString();
            
            //Read trivia answer options
            sb2.append(br.readLine());
            options = sb2.toString().split(","); //split options from comma into array
            
            //Read trivia's correct answer
            sb3.append(br.readLine());
            this.answer = sb3.toString();
        }catch (FileNotFoundException e){
            System.out.println("File not found.");
        } catch(IOException e){
            System.out.println("Problem with file input");
        }
    }
    
    //Method to check given answer with object's correct answer
    public boolean checkAnswer(String answer){
        return (answer.equals(this.answer));
    }
    
    //Accessor methods
    public String getQuestion(){
        return this.question;
    }
    
    public String[] getOptions(){
        return this.options;
    }
    
    public String getAnswer(){
        return answer;
    }
    
    public int getDay(){
        return this.day;
    }
}
