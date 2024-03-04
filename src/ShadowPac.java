import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Code for SWEN20003 Project 2, Semester 1, 2023
 * Yovela Adelina Budiman (Student ID: 1338393)
 * ShadowPac class contains the main method, which is the entry point of the game
 */
public class ShadowPac extends AbstractGame  {

    //ghost objects
    private Pellet pellet;
    private RedGhost redGhost;
    private BlueGhost blueGhost;
    private PacPlayer pacPlayer;
    private PinkGhost pinkGhost;
    private GreenGhost greenGhost;
    private static boolean frenzyMode = false;


    // variables to determine the stage of the game
    private boolean level1ended = false;
    private boolean level0ended = false;
    private boolean level0started = false;
    private boolean level1started = false;
    private boolean level0instructions = true;
    private boolean level1instructions = false;


    // variables for determining whether user wins or not
    private static int frame_count_win = 0;
    private static int frenzy_frame_count = 0;
    private final static int CHERRY_POINTS = 20;
    private final static int SCORE_WIN_LEVEL1 = 800;
    private final static int SCORE_WIN_LEVEL0 = 1210;
    private final static int DOTS_POINTS_LEVEL_0 = 10;


    // variables controlling the starting screen
    private static int score = 0;
    private static int n_hearts = 3;
    private final static int WINDOW_HEIGHT = 768;
    private final static int WINDOW_WIDTH = 1024;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final Point titlePoint = new Point(260, 250);
    private final Image heartImage = new Image("res/heart.png");
    private final Image backgroundImage = new Image("res/background0.png");


    //Arrays to store objects created with data obtained from CSV file after calling readCSV()
    private final ArrayList<Dots> dotsArray = new ArrayList<>();
    private final ArrayList<Walls> wallsArray = new ArrayList<>();
    private final ArrayList<Ghost> ghostArray = new ArrayList<>();
    private final ArrayList<Cherry> cherryArray = new ArrayList<>();

    // font sizes and win and lose messages
    private final static int TITLE_SIZE = 64;
    private final static int INSTRUCTION_SIZE_0 = 24;
    private final static int INSTRUCTION_SIZE_1 = 40;
    private final static String PLAYER_LOSE_MESSAGE = "GAME OVER!";
    private final static String PLAYER_WON_MESSAGE_1 = "WELL DONE!";
    private final static String PLAYER_WON_MESSAGE_0 = "LEVEL COMPLETE!";
    private final Font winMessage = new Font("res/FSO8BITR.ttf", TITLE_SIZE);
    private final Font loseMessage = new Font("res/FSO8BITR.ttf", TITLE_SIZE);
    private final Font titleMessage = new Font("res/FSO8BITR.ttf", TITLE_SIZE);
    private final static String INSTRUCTION_MESSAGE_0 = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE";
    private final Font instructionMessage0 = new Font("res/FSO8BITR.ttf", INSTRUCTION_SIZE_0);
    private final Font instructionMessage1 = new Font("res/FSO8BITR.ttf", INSTRUCTION_SIZE_1);


    /**
     * This method allows the ShadowPac class to inherit from its parent class 'AbstractGame'
     */
    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * This method allows other classes to access the private variable 'frenzyMode'
     * @return the state of the game (whether it is in frenzy mode or not)
     */
    public static boolean getFrenzyMode(){
        return frenzyMode;
    }

    /**
     * Method used to read file and create objects
     * @param filename This parameter determines whether the method will read the
     *                 file for either level 0 or level 1
     */
    private void readCSV(String filename) {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String text;

            //reads through each line and separates each value
            while ((text = br.readLine()) != null) {
                String[] coordinates = text.split(",");
                String objectType = coordinates[0];

                int coordinateX = Integer.parseInt(coordinates[1]);
                int coordinateY = Integer.parseInt(coordinates[2]);

                // creates new objects of each type and stores them into their respective arrays, this includes
                // storing the initial coordinates of each object type
                switch (objectType) {
                    case "Player":
                        pacPlayer = new PacPlayer(coordinateX, coordinateY);
                        break;
                    case "Ghost":
                        ghostArray.add(new Ghost(coordinateX, coordinateY));
                        break;
                    case "Wall":
                        wallsArray.add(new Walls(coordinateX, coordinateY));
                        break;
                    case "Dot":
                        dotsArray.add(new Dots(coordinateX, coordinateY));
                        break;
                    case "Pellet":
                        pellet = new Pellet(coordinateX, coordinateY);
                        break;
                    case "GhostRed":
                        redGhost = new RedGhost(coordinateX, coordinateY);
                        break;
                    case "GhostBlue":
                        blueGhost = new BlueGhost(coordinateX, coordinateY);
                        break;
                    case "GhostPink":
                        pinkGhost = new PinkGhost(coordinateX, coordinateY);
                        break;
                    case "GhostGreen":
                        greenGhost = new GreenGhost(coordinateX, coordinateY);
                        break;
                    case "Cherry":
                        cherryArray.add(new Cherry(coordinateX, coordinateY));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetArrayLists() {
        wallsArray.clear();
        dotsArray.clear();
    }

    /**
     * This method controls the behaviour of game elements if they collide with something
     * that will need them to change (disappear from the screen, move back, etc)
     */
    private void checksForCollisions(){

        // FOR WHEN LEVEL 0 IS BEING PLAYED
        if (!level1started) {
            // Makes sure pac player doesn't go through walls, turns player back depending on
            // the direction that it is facing
            for (Walls walls : wallsArray) {
                if ((pacPlayer.rectangleForm).intersects(walls.rectangleForm)) {
                    pacPlayer.moveBack();
                }
            }


            // checking to see if pacman will collide with a dot, then making the dot disappear if it does
            for (Dots dots : dotsArray) {
                if ((pacPlayer.rectangleForm).intersects(dots.rectangleForm)) {
                    dotsArray.remove(dots);
                    score += DOTS_POINTS_LEVEL_0;
                    break;
                }
            }

            // checking to see if pac player will collide with ghost, then making it go back to initial position
            for (Ghost ghost : ghostArray) {
                if ((pacPlayer.rectangleForm).intersects(ghost.rectangleForm)) {
                    n_hearts -= 1;
                    pacPlayer.resetPosition();
                }
            }

            // FOR WHEN LEVEL 1 IS BEING PLAYED
        } else if (score <= 800) {

            // ghost and player resets positions and lives of player decreases by 1
            // following each collision
            if (!frenzyMode) {
                if ((pacPlayer.rectangleForm).intersects(redGhost.rectangleForm)) {
                    n_hearts -= 1;
                    pacPlayer.resetPosition();
                    redGhost.resetPosition();

                } else if ((pacPlayer.rectangleForm).intersects(blueGhost.rectangleForm)) {
                    n_hearts -= 1;
                    pacPlayer.resetPosition();
                    blueGhost.resetPosition();

                } else if ((pacPlayer.rectangleForm).intersects(greenGhost.rectangleForm)){
                    n_hearts -= 1;
                    pacPlayer.resetPosition();
                    greenGhost.resetPosition();

                } else if ((pacPlayer.rectangleForm).intersects(pinkGhost.rectangleForm)) {
                    n_hearts -= 1;
                    pacPlayer.resetPosition();
                    pinkGhost.resetPosition();
                }

                // ghost disappears after collision with pacplayer and pacplayer earns
                // 30 points
            } else {
                if ((pacPlayer.rectangleForm).intersects(redGhost.rectangleForm)) {
                    score += redGhost.getFrenzyPoint();
                    redGhost.setDrawFalse(redGhost.draw);
                    redGhost.setFrenzyPoint();
                } else if ((pacPlayer.rectangleForm).intersects(blueGhost.rectangleForm)) {
                    score += blueGhost.getFrenzyPoint();
                    blueGhost.setDrawFalse(blueGhost.draw);
                    blueGhost.setFrenzyPoint();
                } else if ((pacPlayer.rectangleForm).intersects(greenGhost.rectangleForm)){
                    score += greenGhost.getFrenzyPoint();
                    greenGhost.setDrawFalse(greenGhost.draw);
                    greenGhost.setFrenzyPoint();
                } else if ((pacPlayer.rectangleForm).intersects(pinkGhost.rectangleForm)) {
                    score += pinkGhost.getFrenzyPoint();
                    pinkGhost.setDrawFalse(pinkGhost.draw);
                    pinkGhost.setFrenzyPoint();
                }
            }

            for (Walls walls : wallsArray) {
                if ((pacPlayer.rectangleForm).intersects(walls.rectangleForm)) {
                    pacPlayer.moveBack();
                }
                if ((pinkGhost.rectangleForm).intersects(walls.rectangleForm)) {
                    pinkGhost.determiningGhostDirection();
                    pinkGhost.moveBack();
                }

                if ((greenGhost.rectangleForm).intersects(walls.rectangleForm)) {
                    if ((greenGhost.getDirection()).equals("Vertical")) {
                        greenGhost.setMovingDown(!greenGhost.getMovingDown());
                    } else {
                        greenGhost.setMovingRight(!greenGhost.getMovingRight());
                    }
                }

                // reversing the direction of ghosts if they intersect with a wall
                if ((blueGhost.rectangleForm).intersects(walls.rectangleForm)) {
                    blueGhost.setMovingDown(!blueGhost.getMovingDown());
                }

                if ((redGhost.rectangleForm).intersects(walls.rectangleForm)) {
                    redGhost.setMovingRight(!redGhost.getMovingRight());
                }
            }
            for (Dots dots : dotsArray) {
                if ((pacPlayer.rectangleForm).intersects(dots.rectangleForm)) {
                    dotsArray.remove(dots);
                    score += DOTS_POINTS_LEVEL_0;
                    break;
                }
            }
            for (Cherry cherry : cherryArray) {
                if ((pacPlayer.rectangleForm).intersects(cherry.rectangleForm)) {
                    cherryArray.remove(cherry);
                    score += CHERRY_POINTS;
                    break;
                }
            }

            if (pellet != null) {
                if ((pacPlayer.rectangleForm).intersects(pellet.rectangleForm)) {
                    frenzyMode = true;
                    pellet = null;
                }
            }
        }
    }

    /**
     * This method draws both the heart and score element in the initial starting point of
     * the game
     */
    public void draw_hearts_and_score(){
        int m = 0;
        for (double i = 0; i < n_hearts; i++) {
            heartImage.drawFromTopLeft(900 + m, 10);
            m += 30;
        }
        Font scoreMessage = new Font("res/FSO8BITR.ttf", 20);
        scoreMessage.drawString("Score " + score, 25, 25);
    }

    /**
     * This method makes ghosts return to starting position if it disappeared during frenzy mode
     * and makes ghosts and players return to original speed (before frenzy mode)
     */
    public void resetAfterFrenzyMode(){
        frenzyMode = false;
        pacPlayer.normalStepSize();
        blueGhost.setStepSize("nonFrenzy");
        greenGhost.setStepSize("nonFrenzy");
        pinkGhost.setStepSize("nonFrenzy");
        redGhost.setStepSize("nonFrenzy");

        if (!blueGhost.draw){
            blueGhost.resetPosition();
            blueGhost.setDrawTrue(blueGhost.draw);
        }
        if (!greenGhost.draw) {
            greenGhost.resetPosition();
            greenGhost.setDrawTrue(greenGhost.draw);

        }
        if (!pinkGhost.draw) {
            pinkGhost.resetPosition();
            pinkGhost.setDrawTrue(pinkGhost.draw);

        }
        if (!redGhost.draw) {
            redGhost.resetPosition();
            redGhost.setDrawTrue(redGhost.draw);

        }
    }

    /**
     * This method draws all the game elements present in level 1
     */
    public void drawAndMoveLevel1(){
        for (Walls walls : wallsArray) {
            walls.drawWalls();
        }
        for (Dots dots : dotsArray) {
            dots.drawDots();
        }
        for (Cherry cherry : cherryArray) {
            cherry.drawCherry();
        }
        if (pellet != null) {
            pellet.drawPellet();
        }
        if (blueGhost.draw) {
            blueGhost.movingBlueGhost();
        }
        if (pinkGhost.draw) {
            pinkGhost.movingPinkGhost();
        }
        if (redGhost.draw) {
            redGhost.movingRedGhost();
        }
        if (greenGhost.draw) {
            greenGhost.movingGreenGhost();
        }
    }

    /**
     * This method ensures that the section of the game that is being displayed
     * suits the user's progress in the game
     * @param input This parameter lets us know what the user wants the game to do
     */
    public void checkingGameSection(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
            // either press W and level1 instructions not displayed,
        } else if (n_hearts == 0){
            level0ended = true;
            level1ended = true;
            backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            loseMessage.drawString(PLAYER_LOSE_MESSAGE, ((Window.getWidth()) / 2) - ((loseMessage.getWidth("GAME OVER!")) / 2), (((Window.getHeight()) / 2) + 32));

        } else if (level0ended && !level1instructions) {
            level1instructions = true;
            resetArrayLists();

        } else if ((input.wasPressed(Keys.W)) && (!level1instructions)){
            level1instructions = true;
            level0instructions = false;
            level0ended = true;

            // if submitting, just delete the statement for level0ended above then delete the one fore level0 below
        } else if ((input.wasPressed(Keys.SPACE)) && !level0started && !level0ended) {
            readCSV("res/level0.csv");
            level0instructions = false;
            level0started = true;

        } else if ((input.wasPressed(Keys.SPACE)) && level1instructions && !level1started) {
            level1started = true;
            resetArrayLists();
            readCSV("res/level1.csv");
            score = 0;
        }

    }
    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     * @param input This parameter indicates what the user wants (or is pressing)
     * while playing the game
     */
    @Override
    protected void update(Input input) {
        checkingGameSection(input);

        // print out the title page if the space button was not pressed
        if (level0instructions) {
            backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            titleMessage.drawString(GAME_TITLE, titlePoint.x, titlePoint.y);
            instructionMessage0.drawString(INSTRUCTION_MESSAGE_0, 320, 440);

            // start the game after space button was pressed
        } else if ((level0started) && (!level0ended)) {
            if ((score != SCORE_WIN_LEVEL0)) {
                backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
                draw_hearts_and_score();

                // drawing each element from the array of coordinates of game elements
                for (Ghost ghost : ghostArray) {
                    ghost.drawGhost();
                }
                for (Walls walls : wallsArray) {
                    walls.drawWalls();
                }
                for (Dots dots : dotsArray) {
                    dots.drawDots();
                }

                pacPlayer.movePacPlayer(input);
                checksForCollisions();

                // if pac player has eaten all the dots and the user has won
            } else {
                if (frame_count_win == 300){
                    level0ended = true;
                }
                frame_count_win += 1;
                backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
                winMessage.drawString(PLAYER_WON_MESSAGE_0, ((Window.getWidth()) / 2) - ((winMessage.getWidth(PLAYER_WON_MESSAGE_0)) / 2), (((Window.getHeight()) / 2) + 32));

            }
        } else if (level1instructions && !level1started) {
            backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            instructionMessage1.drawString("PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE\nEAT THE PELLET TO ATTACK", 200, 350);

        } else if (level1started && (score < SCORE_WIN_LEVEL1) && !level1ended) {
            backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            draw_hearts_and_score();
            drawAndMoveLevel1();
            pacPlayer.movePacPlayer(input);

            // START OF LEVEL 1
            if (frenzyMode) {
                pacPlayer.frenzyStepSize();
                if (frenzy_frame_count == 0) {
                    blueGhost.setStepSize("frenzy");
                    pinkGhost.setStepSize("frenzy");
                    greenGhost.setStepSize("frenzy");
                    redGhost.setStepSize("frenzy");
                }
                if (frenzy_frame_count == 1000) {
                    resetAfterFrenzyMode();
                }
                frenzy_frame_count += 1;
            }
            checksForCollisions();

            // player has won level 1!
        } else if (score >= SCORE_WIN_LEVEL1) {
            level1ended = true;
            backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            winMessage.drawString(PLAYER_WON_MESSAGE_1, ((Window.getWidth()) / 2) - ((winMessage.getWidth(PLAYER_WON_MESSAGE_1)) / 2), (((Window.getHeight()) / 2) + 32));
        }
    }
}