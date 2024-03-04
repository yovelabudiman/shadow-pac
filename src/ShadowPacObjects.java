import bagel.util.Rectangle;

/**
 * This class is created to be the parent class of all other classes in the game
 */
public class ShadowPacObjects {
    protected double x;
    protected double y;
    protected Rectangle rectangleForm;

    /**
     * This method is a constructor for the ShadowPacObjects class
     */
    public ShadowPacObjects(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

