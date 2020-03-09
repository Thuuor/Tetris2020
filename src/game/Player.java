/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author 10229590
 */
public class Player implements Comparable <Player>{
    
    private String name;
    private int score;
    
    public Player(String name, int score){
        this.name = name;
        this.score = score;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public int getScore(){
        return score;
    }
    
    public void setScore(int newScore){
        score = newScore;
    }

    @Override
    public int compareTo(Player p) {
        if (score < p.score){
            return 1;
        } else if(score < p.score) {
            return -1;
        } else {
            return 0;
        }
    }
    
}
