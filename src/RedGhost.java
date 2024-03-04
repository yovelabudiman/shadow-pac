import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * This class is created for red ghosts in the game
 */
public class RedGhost extends Ghost {
    private Point position;
    private double stepSize = 1;
    private final Point startingPosition;
    private static boolean movingRight = true;
    private final Image redGhostImage = new Image("res/ghostRed.png");
    private final double redGhostHeight = redGhostImage.getHeight();
    private final double redGhostWidth = redGhostImage.getWidth();

    /**
     * This constructor is used to initialise the position and initial direction of the
     * ghosts as well as create a new rectangle version of the ghost
     * @param x This is the first parameter that determines the x position of ghost
     * @param y This is the second parameter that determines the y position of ghost
     */
    public RedGhost(double x, double y){
        super(x, y);
        this.position = new Point(x, y);
        this.startingPosition = position;
        rectangleForm = new Rectangle(x, y, redGhostWidth, redGhostHeight);
    }

    /**
     * This method is used to change the speed of each ghost depending on whether or not
     * the game is in frenzyMode.
     * @param mode This is the only parameter that will indicate what mode the game is in
     */
    public void setStepSize(String mode){
        if (mode.equals("frenzy")) {
            stepSize -= 0.5;
        } else {
            stepSize += 0.5;
        }
    }

    /**
     * Method that moves the ghost given the direction - code implemented
     * from project 1 sample solution provided by teaching team
     */
    public void updatingPosition(double xMove, double yMove){
        position = new Point (position.x + xMove, position.y + yMove);
    }

    /**
     * This method allows other classes to access the private variable 'movingRight'
     * @return movingRight This shows whether ghost is moving right or not
     */
    public boolean getMovingRight(){
        return movingRight;
    }

    /**
     * This method moves the ghost by updating its position by every stepSize (speed)
     */
    public void movingRedGhost(){
        if (movingRight) {
            updatingPosition(stepSize, 0);

        } else {
            updatingPosition(-stepSize, 0);
        }

        rectangleForm = new Rectangle(position.x, position.y, redGhostWidth, redGhostHeight);

        drawRedGhost();
    }

    /**
     * This allows the ghost to move in the opposite direction when called if ghost collides with wall
     * @param opposite This parameter refers to whether the ghost is moving right or not
     */
    public void setMovingRight(boolean opposite){
        movingRight = opposite;
    }

    /**
     * Method used to draw the red ghost
     */
    public void drawRedGhost(){
        if (!(ShadowPac.getFrenzyMode())) {
            redGhostImage.drawFromTopLeft(position.x, position.y);
        } else {
            (Ghost.getFrenzyGhostImage()).drawFromTopLeft(position.x, position.y);
        }
    }

    /**
     * Method that resets the player's position to the starting location - code
     * implemented from project 1 sample solution provided by teaching team
     */
    public void resetPosition(){
        position = startingPosition;
    }
}