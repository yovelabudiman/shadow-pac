import bagel.*;
import bagel.util.*;

/**
 * This class is created for the pacplayer in the game
 */
public class PacPlayer extends ShadowPacObjects {
    private Point position;
    private Point prevPosition;
    private Image currentImage;
    private int frameCount = 0;
    private boolean open = false;
    private static double stepSize = 3;
    private final Point startingPosition;
    private final static int SWITCH_FACE = 15;


    // pac player drawing settings
    private final String pacImage = "res/pac.png";
    private final String pacOpenImage = "res/pacOpen.png";
    private final DrawOptions rotationOption = new DrawOptions();

    /**
     * This constructor is used to initialise the position of the player
     * @param x This is the first parameter that determines the x initial position of player
     * @param y This is the second parameter that determines the y initial position of player
     */
    public PacPlayer(double x, double y){
        super(x, y);
        this.position = new Point(x, y);
        this.startingPosition = position;
        this.currentImage = new Image(pacImage);
    }

    /**
     * This method sets the player's speed to the new one when the game is in frenzy mode
     */
    public void frenzyStepSize() { stepSize = 4; }

    /**
     * This method makes the player's speed return to normal after frenzy mode ends
     */
    public void normalStepSize() { stepSize = 3; }

    /**
     * Method obtaining user's input to determine the direction that the user wants pac player to face and move
     * player's coordinates for drawing after
     */
    public void movePacPlayer(Input input) {
        if (input.isDown(Keys.LEFT)) {
            updatingPosition(-stepSize, 0);
            rotationOption.setRotation(Math.PI);

        } else if (input.isDown(Keys.RIGHT)) {
            updatingPosition(stepSize, 0);
            // amount of rotations required to change direction of pac player depending on user input
            rotationOption.setRotation(0);

        } else if (input.isDown(Keys.UP)) {
            updatingPosition(0, -stepSize);
            rotationOption.setRotation(Math.toRadians(270));

        } else if (input.isDown(Keys.DOWN)) {
            updatingPosition(0, stepSize);
            rotationOption.setRotation(Math.PI / 2);

        }
        rectangleForm = new Rectangle(position.x, position.y, currentImage.getWidth(), currentImage.getHeight());
        frameCount += 1;

        if (frameCount == SWITCH_FACE) {
            if (open) {
               currentImage = new Image(pacOpenImage);
               open = false;
            } else {
                currentImage = new Image(pacImage);
                open = true;
            }
            frameCount = 0;
        }
        currentImage.drawFromTopLeft(position.x, position.y, rotationOption);
    }

    /**
     * Method that moves the player given the direction - code implemented
     * from project 1 sample solution provided by teaching team
     */
    public void updatingPosition(double xMove, double yMove){
        prevPosition = position;
        position = new Point (position.x + xMove, position.y + yMove);
    }

    /**
     * Method that prevents the player from colliding with the walls - code
     * implemented from project 1 sample solution provided by teaching team
     */
    public void moveBack() {
        position = prevPosition;
    }

    /**
     * Method that resets the player's position to the starting location - code
     * implemented from project 1 sample solution provided by teaching team
     */
    public void resetPosition(){
        position = startingPosition;
        currentImage = new Image(pacImage);
        rotationOption.setRotation(0);
    }
}
