import bagel.*;
import bagel.util.Rectangle;

/**
 * This class is for the pellet objects in the game
 */
public class Pellet extends ShadowPacObjects {
    private final Image pelletImage = new Image("res/pellet.png");

    /**
     * This is the constructor for the pellet class
     * @param x This is the x position of the pellet
     * @param y This is the y position of the pellet
     */
    public Pellet(double x, double y){
        super(x, y);
        double pelletHeight = pelletImage.getHeight();
        double pelletWidth = pelletImage.getWidth();
        rectangleForm = new Rectangle(x, y, pelletWidth, pelletHeight);
    }

    /**
     * Method used to draw the pellet
     */
    public void drawPellet(){
        pelletImage.drawFromTopLeft(x, y);
    }
}
