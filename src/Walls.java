import bagel.*;
import bagel.util.Rectangle;

/**
 * This class is for the wall objects in the game
 */
public class Walls extends ShadowPacObjects {
    private final Image wallImage = new Image("res/wall.png");
    private final double wallHeight = wallImage.getHeight();
    private final double wallWidth = wallImage.getWidth();

    /**
     * This is a constructor for the walls class
     * @param x This is the x position of each wall object in the game
     * @param y This is the y position of each wall object in the game
     */
    public Walls(double x, double y){
        super(x, y);
        rectangleForm = new Rectangle(x, y, wallWidth, wallHeight);
    }

    /**
     * Method used to draw each wall
     */
    public void drawWalls(){
        wallImage.drawFromTopLeft(x, y);
    }
}
