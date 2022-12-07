package src;

import src.brick_strategies.CollisionStrategy;
import danogl.collisions.Layer;
import danogl.util.Counter;
import src.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    // ====================== window messages ======================
    public static final String PROMPT = "";
    public static final String YOU_WIN = "You win!";
    public static final String YOU_LOSE = "You Lose!";
    public static final String PLAY_AGAIN = " Play again?";
    public static final String WINDOW_TITLE = "Bouncing Ball";
    // ====================== constant sizes ======================
    private static final float BORDER_WIDTH = 20;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 40;
    public static final float BALL_SPEED = 350;
    public static final int ZERO = 0;
    public static final int COLUMNS_OF_BRICKS = 7;
    public static final int ROWS_OF_BRICKS = 8;
    public static final float HALF = 0.5f;
    public static final int WIDGET_SIZE = 30;
    public static final int NUM_OF_LIVES = 3;
    public static final int WIDGETS_LEN = NUM_OF_LIVES * WIDGET_SIZE;
    public static final int MIN_DIST_FROM_EDGE = 15;
    public static final int TWO = 2;
    public static final int OPPOSITE_DIRECTION = -1;
    public static final int PADDLE_DIST_FROM_BOTTOM = 30;
    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 500;
    // ====================== images paths ======================
    public static final String ASSETS_PADDLE_PNG = "assets/paddle.png";
    public static final String ASSETS_HEART_PNG = "assets/heart.png";
    public static final String ASSETS_BACKGROUND = "assets/DARK_BG2_small.jpeg";
    public static final String ASSETS_BRICK_PNG = "assets/brick.png";
    public static final String ASSETS_BALL_PNG = "assets/ball.png";
    public static final String ASSETS_BLOP_WAV = "assets/blop_cut_silenced.wav";
    // ====================== private fields ======================
    private UserInputListener inputListener;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Counter gamesCounter;
    private Counter brickCounter;
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    // This method initializes a new game. It creates all game objects, sets their values and initial
    // positions and allows the start of a game.
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        //initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.inputListener = inputListener;
        gamesCounter = new Counter(NUM_OF_LIVES);
        brickCounter = new Counter(ROWS_OF_BRICKS * COLUMNS_OF_BRICKS);
        windowDimensions = windowController.getWindowDimensions();

        //create background
        createBackground(imageReader);

        //create bricks
        createBricks(imageReader);

        //create ball
        createBall(imageReader, soundReader, windowController);

        //create paddle
        Renderable paddleImage = imageReader.readImage(
                ASSETS_PADDLE_PNG, false);
        createPaddle(paddleImage, inputListener, windowDimensions);

        //create borders
        createBorders(windowDimensions);

        // create graphic life counter
        Renderable lifeImage = imageReader.readImage(
                ASSETS_HEART_PNG, true);
        Vector2 lifeImageLocation = new Vector2(0, windowDimensions.y());
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(lifeImageLocation, Vector2.ZERO,
                gamesCounter, lifeImage, gameObjects(), NUM_OF_LIVES);
        gameObjects().addGameObject(graphicLifeCounter,Layer.UI);

        // create numeric life counter
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(gamesCounter,
                new Vector2(BORDER_WIDTH + WIDGETS_LEN, (windowDimensions.y() - WIDGET_SIZE)),
                new Vector2(WIDGET_SIZE, WIDGET_SIZE),
                gameObjects());
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);
    }

    private void createBackground(ImageReader imageReader) {
        Renderable background = imageReader.readImage(ASSETS_BACKGROUND, false);
        gameObjects().addGameObject(
                new GameObject(Vector2.ZERO, windowDimensions, background), Layer.BACKGROUND);
    }

    private void createBricks(ImageReader imageReader) {
        float brickWidth = (int) ((windowDimensions.x() - TWO * BORDER_WIDTH) / COLUMNS_OF_BRICKS);
        float brickHeight = (int) ((windowDimensions.y()) / (NUM_OF_LIVES * ROWS_OF_BRICKS));
        for (int i = ZERO; i < ROWS_OF_BRICKS; i++) {
            for (int j = ZERO; j < COLUMNS_OF_BRICKS; j++) {
                Brick brick = createSingleBrick(imageReader, brickWidth, brickHeight);
                int brickX = (int) (BORDER_WIDTH + brickWidth * j + brickWidth * HALF);
                int brickY = (int) (brickHeight * i + brickHeight * HALF);
                brick.setCenter(new Vector2(brickX, brickY));
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private Brick createSingleBrick(ImageReader imageReader, float brickWidth, float brickHeight) {
        CollisionStrategy strategy = new CollisionStrategy(gameObjects());
        Renderable brickImage = imageReader.readImage(ASSETS_BRICK_PNG, false);
        return new Brick(Vector2.ZERO, new Vector2(brickWidth, brickHeight),
                brickImage, strategy, brickCounter);
    }

    private void createBall(
            ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
        Renderable ballImage =
                imageReader.readImage(ASSETS_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(ASSETS_BLOP_WAV);
        ball = new Ball(
                Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(HALF));
        gameObjects().addGameObject(ball);

        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= OPPOSITE_DIRECTION;
        if (rand.nextBoolean())
            ballVelY *= OPPOSITE_DIRECTION;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    private void createPaddle(
            Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions) {
        GameObject Paddle = new Paddle(Vector2.ZERO,  new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions, MIN_DIST_FROM_EDGE);

        Paddle.setCenter(new Vector2(
                windowDimensions.x() / TWO, (int) windowDimensions.y() - PADDLE_DIST_FROM_BOTTOM));
        gameObjects().addGameObject(Paddle);
    }

    private void createBorders(Vector2 windowDimensions) {
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        null), Layer.STATIC_OBJECTS
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x() - BORDER_WIDTH, ZERO),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        null), Layer.STATIC_OBJECTS
        );
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(windowDimensions.x(), BORDER_WIDTH),
                        null), Layer.STATIC_OBJECTS
        );
    }


    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);
        double ballHeight = ball.getCenter().y();
        if (ballHeight > windowDimensions.y()) {
            gamesCounter.decrement();
            ball.setCenter(windowDimensions.mult(HALF));
        }
        checkForGameEnd();
    }

    private void checkForGameEnd() {

        String prompt = PROMPT;
        if (brickCounter.value() == ZERO) {
            prompt = YOU_WIN;
        }
        if (gamesCounter.value() == ZERO) {
            prompt = YOU_LOSE;
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = YOU_WIN;
        }
        if (!prompt.isEmpty()) {
            prompt += PLAY_AGAIN;
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else windowController.closeWindow();
        }
    }

    public static void main(String[] args) {
        new BrickerGameManager(
                WINDOW_TITLE,
                new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT)).run();
    }
}
