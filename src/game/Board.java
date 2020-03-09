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
    public static final int INITAL_ROW = -2;
    
    private Tetrominoes[][] playBoard;
    private Shape currentShape;
    private int currentRow;
    private int currentCol;
    private Timer timer;
    private int deltaTime;
    private MyKeyAdapter keyAdapter;
    private ScoreBoardIncrementer scoreboard;
    private Player player;
    private JFrame parent;
    
    class MyKeyAdapter extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (canMove(currentShape,currentRow,currentCol - 1)) {
                    currentCol--;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (canMove(currentShape,currentRow,currentCol + 1)) {
                    currentCol++;
                }
                break;
            case KeyEvent.VK_UP:
                Shape s = currentShape.rotateRight();
                if (canMove(s,currentRow,currentCol)){
                    currentShape = s;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (canMove(currentShape,currentRow + 1, currentCol)){
                     currentRow++;
                }
                break;
            case KeyEvent.VK_ENTER:
                pauseGame();
                break;
            default:
                break;
        }
        repaint();
    }

    }
    private void pauseGame () throws HeadlessException {
        timer.stop();
        Icon icon = new ImageIcon(getClass().getResource("/images/imagenpause.png"));
        JOptionPane.showMessageDialog(Board.this,"","Pause",JOptionPane.OK_OPTION,icon);
        timer.start();
    }
    
    public void pauseGameAbout() {
        timer.stop();
    }
    
    public void restartGameAbout(){
        timer.start();
    }
    
    
    public Board(){
        super();
        playBoard = new Tetrominoes[NUM_ROWS][NUM_COLS];
        deltaTime = INITIAL_DELTA_TIME;
        createTimer();
        setFocusable(true);
        keyAdapter = new MyKeyAdapter();
        addKeyListener(keyAdapter);
        initGame();
    }
    
    public Board(ScoreBoardIncrementer inc,JFrame parent){
        this();
        this.parent = parent;
        scoreboard = inc;
    }
    
    public void setScoreBoard(ScoreBoardIncrementer scBoard){
        scoreboard = scBoard;
    }
    
    public int getScoreBoard(){
        return scoreboard.getScore();
    }
    
    private void createTimer(){
        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (canMove(currentShape,currentRow + 1, currentCol)){
                    currentRow++;
                    repaint();
                    Toolkit.getDefaultToolkit().sync();
                } else {
                    if (currentRow == INITAL_ROW){
                        GameOver();
                    }
                    moveCurrentShapeToBoard();
                    checkCompletedLines();
                    resetCurrentShape();
                }
            }
        });
    }
    
    private void moveCurrentShapeToBoard() {
        for (int i = 0; i < 4;i ++){
            int row = currentRow + currentShape.getY(i);
            int col = currentCol + currentShape.getX(i);
            if (row >= 0){
                playBoard[row][col] = currentShape.getShape();
            }
            
        }
    }
    
    private void resetCurrentShape(){
        currentRow = INITAL_ROW;
        currentCol = NUM_COLS / 2;
        currentShape = new Shape();
        currentShape.setRandomShape();
    }
    
    public void initGame(){
        for (int row = 0; row < playBoard.length; row ++){
            for (int col = 0; col < playBoard[0].length; col ++ ){
                playBoard[row][col] = Tetrominoes.NoShape;
            }
        }
        resetCurrentShape();
        timer.start();
        repaint();
    } 
    
    private boolean canMove(Shape shape,int newRow, int newCol){
        int leftBorder = newCol + shape.minX();
        int rightBorder = newCol + shape.maxX();
        
        if (leftBorder < 0 || newRow + shape.maxY() >= NUM_ROWS || rightBorder >= NUM_COLS){
            return false;
        }
        if (currentPieceHitsBoard(newRow,newCol)){
            return false;
        }
        return true;
    }
    
    public boolean currentPieceHitsBoard(int newRow, int newCol){
        for (int i = 0; i < 4; i++){
            int row = newRow + currentShape.getY(i);
            int col = newCol + currentShape.getX(i);
            if (row >= 0){
                if (playBoard[row][col] != Tetrominoes.NoShape){
                return true;
                }
            }
            
        }
        return false;
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
    
    public Timer getTimer(){
        return timer;
    }

    public void checkCompletedLines(){
       
       for (int row = 0; row < NUM_ROWS; row++) {
           int count = 0;
           for (int col = 0; col < NUM_COLS; col++) {
               if(playBoard[row][col] != Tetrominoes.NoShape){
                   count++;
               }
           }
           if (count == NUM_COLS){
               delLines(row);
               scoreboard.incrementScore(10);
           }
       }
   }
   
   public void delLines(int rowDeleted){
       for (int row = rowDeleted; row > 1; row--) {
           for (int col = 0; col < NUM_COLS; col++) {
               playBoard[row][col] = playBoard[row-1][col];
           }
       }
       for (int col = 0; col < NUM_COLS;col ++){
           playBoard[0][col] = Tetrominoes.NoShape;
       }
   }
   
   public void NewGame(){
       initGame();
       scoreboard.resetScore();
   }
   
   public void GameOver(){
       timer.stop();
       int option = JOptionPane.showConfirmDialog(null, "Game over man, your score is: " + scoreboard.getScore() + ". You want to save your score?");
       if (option == JOptionPane.YES_OPTION){
           SetNameInPlayer();
           NewGame();
        } else {
           NewGame();
        }

   }

    public void SetNameInPlayer() {
        NameOfWinner nofw = new NameOfWinner(parent, true);
        nofw.setVisible(true);
    }
   
}
