package game;


/**
 *
 * @author 10229590
 */
public class Shape {
    private static final int[][][] coordsTable = new int[][][]{
        {{0, 0}, {0, 0}, {0, 0}, {0, 0}}, 
        {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}, 
        {{0, -1}, {0, 0}, {1, 0}, {1, 1}}, 
        {{0, -1}, {0, 0}, {0, 1}, {0, 2}}, 
        {{-1, 0}, {0, 0}, {1, 0}, {0, 1}}, 
        {{0, 0}, {1, 0}, {0, 1}, {1, 1}}, 
        {{-1, -1}, {0, -1}, {0, 0}, {0, 1}}, 
        {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
    };
    
    private Tetrominoes pieceShape;
    private int[][] coords;
    
    public Shape(){
        coords = new int[4][2];
        for (int i = 0; i < 4;i ++){
            for (int j = 0; j < 2; j++){
                coords[i][j] = 0;
            }
        }
        pieceShape = Tetrominoes.NoShape;
    }
    
    public Shape(Tetrominoes shapeType){
        this();
        setShape(shapeType);
    }
    
    public void setShape(Tetrominoes shapeType){
        for (int i = 0;i < 4;i ++){
            for (int j = 0; j < 2;j ++){
                coords[i][j] = coordsTable[shapeType.ordinal()][i][j];
            }
        }
        pieceShape = shapeType;
    }
    
    private void setX(int index, int x){
        coords[index][0] = x;
    }
    
    private void setY(int index, int y){
        coords[index][1] = y;
    }
    
    public int getX(int index){
        return coords[index][0];
    }
    
    public int getY(int index){
        return coords[index][1];
    }
    
    public Tetrominoes getShape(){
        return pieceShape;
    }
    
    public void setRandomShape(){
        int rand = (int) (Math.random() * 7 + 1);
        Tetrominoes[] tetrominoes = Tetrominoes.values();
        setShape(tetrominoes[rand]);
    }
    
    public int minX(){
        int minX = coords[0][0];
        for(int i=1; i<4; i++) {
            if (coords[i][0] < minX) {
                minX = coords[i][0];
            }
        }
        return minX;
    }
    
    public int minY(){
        int minY = coords[0][1];
        for(int i=1; i<4; i++) {
            if (coords[i][1] < minY) {
                minY = coords[i][1];
            }
        }
        return minY;
    }
    
    public int maxX(){
        int maxX = coords[0][0];
        for(int i=1; i<4; i++) {
            if (coords[i][0] > maxX) {
                maxX = coords[i][0];
            }
        }
        return maxX;
    }
    
    public int maxY(){
        int maxY = coords[0][1];
        for(int i=1; i<4; i++) {
            if (coords[i][1] > maxY) {
                maxY = coords[i][1];
            }
        }
        return maxY;
    }
    /*
    public Shape rotateLeft(){
        
    }
    */
    public Shape rotateRight(){
        if (pieceShape == Tetrominoes.SquareShape){
            return this;
        }
        Shape newShape = new Shape(pieceShape);
        for (int i = 0; i < 4; i ++){
            newShape.setX(i, -getY(i));
            newShape.setY(i, getX(i));
        }
        return newShape;
    }
    
}
