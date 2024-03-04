import bagel.*;
import bagel.util.*;

/**
 * This class is for the dot objects in the game
 */
public class Dots extends ShadowPacObjects {
    private final Image dotImage = new Image ("res/dot.png");
    private final double dotWidth = dotImage.getWidth();
    private final double dotHeight = dotImage.getHeight();

    /**
     * This is the constructor for the dots class
     * @param x This is the x position of each dot
     * @param y This is the y position of each dot
     */
    public Dots(double x, double y){
        super(x, y);
        rectangleForm = new Rectangle(x, y, dotWidth, dotHeight);
    }

    /**
     * Method used to draw each dot
     */
    public void drawDots(){
        dotImage.drawFromTopLeft(x, y);
    }
}

