import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * This class is for the cherry objects in the game
 */
public class Cherry extends ShadowPacObjects {
    private final Image cherryImage = new Image("res/cherry.png");

    /**
     * This is the constructor for the cherry class
     * @param x This is the x position of each cherry
     * @param y This is the y position of each cherry
     */
    public Cherry(double x, double y){
        super(x, y);
        Point position = new Point(x, y);
        double cherryHeight = cherryImage.getHeight();
        double cherryWidth = cherryImage.getWidth();
        rectangleForm = new Rectangle(x, y, cherryWidth, cherryHeight);
    }

    /**
     * Method used to draw the cherry
     */
    public void drawCherry(){
        cherryImage.drawFromTopLeft(x, y);
    }
}