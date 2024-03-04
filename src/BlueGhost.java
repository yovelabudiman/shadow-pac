import bagel.*;
import bagel.util.*;

/**
 * This class is created for blue ghosts in the game
 */
public class BlueGhost extends Ghost {
    private Point position;
    private double stepSize = 2;
    private boolean movingDown = true;
    private final Point startingPosition;
    private final Image blueGhostImage = new Image("res/ghostBlue.png");
    private final double blueGhostHeight = blueGhostImage.getHeight();
    private final double blueGhostWidth = blueGhostImage.getWidth();

    /**
     * This constructor is used to initialise the position and initial direction of the
     * ghosts as well as create a new rectangle version of the ghost
     * @param x This is the first parameter that determines the x position of ghost
     * @param y This is the second parameter that determines the y position of ghost
     */
    public BlueGhost(double x, double y){
        super(x, y);
        this.position = new Point(x, y);
        this.startingPosition = position;
    }

    /**
     * This method is used to change the speed of each ghost depending on whether or not
     * the game is in frenzyMode
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
     * This method allows other classes to access the private variable 'movingDown'
     * @return movingDown This shows whether ghost is moving down or not
     */
    public boolean getMovingDown(){
        return movingDown;
    }

    /**
     * This allows the ghost to move in the opposite direction when called if ghost collides with wall
     * @param opposite This parameter refers to whether the ghost is moving down or not
     */
    public void setMovingDown(boolean opposite){
        movingDown = opposite;
    }

    /**
     * Method that moves the ghost given the direction - code implemented
     * from project 1 sample solution provided by teaching team
     */
    public void updatingPosition(double xMove, double yMove){
        position = new Point (position.x + xMove, position.y + yMove);
    }

    /**
     * This method moves the ghost by updating its position by every stepSize (speed)
     */
    public void movingBlueGhost(){
            if (movingDown) {
                updatingPosition(0, -stepSize);

            } else {
                updatingPosition(0, stepSize);
            }
            rectangleForm = new Rectangle(position.x, position.y, blueGhostWidth, blueGhostHeight);
            drawBlueGhost();
    }

    /**
     * Method used to draw the blue ghost
     */
    public void drawBlueGhost(){
        if (!(ShadowPac.getFrenzyMode())) {
            blueGhostImage.drawFromTopLeft(position.x, position.y);
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