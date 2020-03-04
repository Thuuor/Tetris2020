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
public interface ScoreBoardIncrementer {
    public void incrementScore(int increment);
    public void resetScore();
    public int getScore();
}