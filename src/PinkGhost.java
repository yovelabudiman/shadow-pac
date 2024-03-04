import bagel.*;
import bagel.util.*;
import java.util.Random;

/**
 * This class is created for pink ghosts in the game
 */
public class PinkGhost extends Ghost {
    private Point position;
    private int direction = 0;
    private Point prevPosition;
    private double stepSize = 3;
    private final Point startingPosition;
    private final Image pinkGhostImage = new Image("res/ghostPink.png");
    private final double pinkGhostWidth = pinkGhostImage.getWidth();
    private final double pinkGhostHeight = pinkGhostImage.getHeight();

    /**
     * This constructor is used to initialise the position and initial direction of the
     * ghosts as well as create a new rectangle version of the ghost
     * @param x This is the first parameter that determines the x position of ghost
     * @param y This is the second parameter that determines the y position of ghost
     */
    public PinkGhost(double x, double y){
        super(x, y);
        this.position = new Point(x, y);
        startingPosition = position;
        rectangleForm = new Rectangle(x, y, pinkGhostWidth, pinkGhostHeight);
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
        prevPosition = position;
        position = new Point (position.x + xMove, position.y + yMove);
    }

    /**
     * Method moving the pink ghost according to the direction that was randomly chosen
     */
    public void determiningGhostDirection() {
        Random randomObject = new Random();
           int upperbound = 4;
        direction = randomObject.nextInt(upperbound);

    }

    /**
     * This method moves the ghost by updating its position by every stepSize (speed)
     */
    public void movingPinkGhost(){
            if (direction == 0) {
                updatingPosition(stepSize, 0);

            } else if (direction == 1) {
                updatingPosition(-stepSize, 0);

            } else if (direction == 2) {
                updatingPosition(0, stepSize);

            } else {
                updatingPosition(0, -stepSize);
            }
            rectangleForm = new Rectangle(position.x, position.y, pinkGhostWidth, pinkGhostHeight);
            drawPinkGhost();
    }

    /**
     * Method that prevents the player from colliding with the walls - code
     * implemented from project 1 sample solution provided by teaching team
     */
    public void moveBack() {
        position = prevPosition;
    }

    /**
     * Method used to draw the pink ghost
     */
    public void drawPinkGhost(){

        if (!(ShadowPac.getFrenzyMode())) {
            pinkGhostImage.drawFromTopLeft(position.x, position.y);
        } else {
            (Ghost.getFrenzyGhostImage()).drawFromTopLeft(position.x, position.y);
        }
    }

    /**
     * Method that resets the player's position to the starting location - code
     * implemented from project 1 sample solution provided by teaching team
     */
    public void resetPosition(){ position = startingPosition; }
}