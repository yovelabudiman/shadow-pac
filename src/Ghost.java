import bagel.*;
import bagel.util.Rectangle;

/**
 * This class is created for ghosts in the game. It is the parent class of the other
 * types of ghost classes that will appear in level 1
 */

public class Ghost extends ShadowPacObjects {
    private int FRENZY_POINT = 30;
    protected boolean draw = true;
    private final Image ghostImage = new Image("res/ghostRed.png");
    private static final Image frenzyGhostImage = new Image("res/ghostFrenzy.png");

    /**
     * This constructor is used to initialise the initial position of the
     * ghost as well as create a new rectangle version of the ghost
     * @param x This is the first parameter that determines the initial x position of ghost
     * @param y This is the second parameter that determines the initial y position of ghost
     */
    public Ghost(double x, double y){
        super(x, y);
        double ghostWidth = ghostImage.getWidth();
        double ghostHeight = ghostImage.getHeight();
        rectangleForm = new Rectangle(x, y, ghostWidth, ghostHeight);
    }

    /**
     * This method allows other class to access the FRENZY_POINT variable
     */
    public int getFrenzyPoint(){ return FRENZY_POINT; }

    /**
     * This method sets the frenzy point to zero after ghost collides with pacplayer
     */
    public void setFrenzyPoint(){ FRENZY_POINT = 0; }

    /**
     * This method allows other class to access the frenzyGhost image
     */
    public static Image getFrenzyGhostImage(){ return frenzyGhostImage;}

    /**
     * This method sets the draw variable to 'false' to make elements disappear from the screen
     */
    public final void setDrawFalse(boolean draw){
        this.draw = false;
    }

    /**
     * This method sets the draw variable to 'true' to make elements reappear in the screen
     */
    public final void setDrawTrue(boolean draw){
        this.draw = true;
    }

    /**
     * Method used to draw each ghost
     */
    public void drawGhost(){
        ghostImage.drawFromTopLeft(x, y);
    }


}