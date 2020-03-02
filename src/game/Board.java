/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author 10229590
 */

public class Board extends JPanel{
    
    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;
    public static final int INITIAL_DELTA_TIME = 500;
    
    private Tetrominoes[][] playBoard;
    private Shape currentShape;
    private int currentRow;
    private int currentCol;
    private static Timer timer;
    private int deltaTime;
    private MyKeyAdapter keyAdapter;
    
    class MyKeyAdapter extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (canMove(currentRow,currentCol - 1)) {
                    currentCol--;
                }
                
                break;
            case KeyEvent.VK_RIGHT:
                    currentCol++;
                break;
            case KeyEvent.VK_UP:
                
                break;
            case KeyEvent.VK_DOWN:
                    currentRow++;
                break;
            case KeyEvent.VK_ENTER:
                timer.stop();
                ResumeBotton resumeBotton = new ResumeBotton();
                resumeBotton.setVisible(true);
                break;
            default:
                break;
        }
        repaint();
    }
}
    
    public Board(){
        super();
        setFocusable(true);
        playBoard = new Tetrominoes[NUM_ROWS][NUM_COLS];
        deltaTime = INITIAL_DELTA_TIME;
        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                currentRow++;
                
                repaint();
            }
        });
        keyAdapter = new MyKeyAdapter();
        addKeyListener(keyAdapter);
        initGame();
        
    }
    
    public void initGame(){
        for (int row = 0; row < playBoard.length; row ++){
            for (int col = 0; col < playBoard[0].length; col ++ ){
                playBoard[row][col] = Tetrominoes.NoShape;
            }
        }
        currentRow = 0;
        currentCol = NUM_COLS / 2;
        currentShape = new Shape();
        currentShape.setRandomShape();
        timer.start();
        
    } 
    
    private boolean canMove(int newRow, int newCol){
        int leftBorder = newCol + currentShape.minX();
        int rightBorder = newCol + currentShape.maxX(); 
        
        if (leftBorder < 0 || rightBorder >= NUM_COLS){
            return false;
        }
        return true;
    }
    
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintPlayBoard(g2d);
        paintShape(g2d);
    }
    
    private void paintPlayBoard(Graphics2D g2d){
        
        for (int row = 0; row < playBoard.length;row ++){
            for (int col = 0; col < playBoard[0].length;col ++){
                drawSquare(g2d, row, col, playBoard[row][col]);
            }
        }
    }
    
    private void paintShape(Graphics2D g2d){
        
        for (int i = 0; i < 4; i++){
            int x = currentCol + currentShape.getX(i);
            int y = currentRow + currentShape.getY(i);
            
            drawSquare(g2d, y, x, currentShape.getShape());
        }
    }
    
    private int squareWidth(){
        return getWidth() / NUM_COLS;
    }
    
    private int squareHeight(){
        return getHeight()/ NUM_ROWS;
    }
    
    private void drawSquare(Graphics2D g, int row, int col, Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102), 
            new Color(102, 204, 102), new Color(102, 102, 204), 
            new Color(204, 204, 102), new Color(204, 102, 204), 
            new Color(102, 204, 204), new Color(218, 170, 0)};
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
    }
    
    public static Timer getTimer(){
        return timer;
    }
}
