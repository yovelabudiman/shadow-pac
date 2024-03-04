import bagel.*;
import bagel.util.*;
import java.util.Random;

/**
 * This class is created for green ghosts in the game
 */
public class GreenGhost extends Ghost {
    private Point position;
    private String direction;
    private double stepSize = 4;
    private final Point startingPosition;
    private boolean movingDown = true;
    private boolean movingRight = true;

    private final Image greenGhostImage = new Image("res/ghostGreen.png");
    private final double greenGhostHeight = greenGhostImage.getHeight();
    private final double greenGhostWidth = greenGhostImage.getWidth();


    /**
     * This constructor is used to initialise the position and initial direction of the
     * ghosts as well as create a new rectangle version of the ghost
     * @param x This is the first parameter that determines the x position of ghost
     * @param y This is the second parameter that determines the y position of ghost
     */
    public GreenGhost(double x, double y){
        super(x, y);
        this.direction = "Undetermined";
        this.position = new Point(x, y);
        this.startingPosition = position;
        rectangleForm = new Rectangle(x, y, greenGhostWidth, greenGhostHeight);
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
     * The method allows other classes to access the private 'direction' variable
     * @return the direction that the ghost is facing
     */
    public String getDirection() {
        return direction;
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
     * @param opposite This parameter refers to whether the ghost will be moving down or not
     */
    public void setMovingDown(boolean opposite){ movingDown = opposite; }

    /**
     * This method allows other classes to access the private variable 'movingRight'
     * @return movingRight This shows whether ghost is moving right or not
     */
    public boolean getMovingRight(){
        return movingRight;
    }

    /**
     * This allows the ghost to move in the opposite direction when called if ghost collides with wall
     * @param opposite This parameter refers to whether the ghost will be moving right or not
     */
    public void setMovingRight(boolean opposite){ movingRight= opposite; }

    /**
     * Method moving the green ghost according to the direction that was randomly chosen
     */
    public String determiningGhostDirection() {
        Random randomObject = new Random();
        int upperbound = 2;
        int randomInt = randomObject.nextInt(upperbound);

        if (randomInt == 0) {
            return "Vertical";
        } else {
            return "Horizontal";
        }
    }

    /**
     * This method moves the ghost by updating its position by every stepSize (speed)
     */
    public void movingGreenGhost(){

            if (direction.equals("Undetermined")) {
                direction = determiningGhostDirection();
            }

            if (direction.equals("Vertical")) {
                if (movingDown) {
                    updatingPosition(0, -stepSize);
                } else {
                    updatingPosition(0, stepSize);
                }
            } else if (direction.equals("Horizontal")) {
                if (movingRight) {
                    updatingPosition(-stepSize, 0);
                } else {
                    updatingPosition(stepSize, 0);
                }
            }
            rectangleForm = new Rectangle(position.x, position.y, greenGhostWidth, greenGhostHeight);
            drawGreenGhost();
    }

    /**
     * Method used to draw the green ghost
     */
    public void drawGreenGhost(){
        if (!(ShadowPac.getFrenzyMode())) {
            greenGhostImage.drawFromTopLeft(position.x, position.y);
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