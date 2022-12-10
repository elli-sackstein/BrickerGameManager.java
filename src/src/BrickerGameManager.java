package src;

import src.brick_strategies.*;
import danogl.collisions.Layer;
import danogl.util.Counter;
import src.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class BrickerGameManager extends GameManager {
    // ====================== window messages ======================
    private static final String PROMPT = "";
    private static final String YOU_WIN = "You win!";
    private static final String YOU_LOSE = "You Lose!";
    private static final String PLAY_AGAIN = " Play again?";
    private static final String WINDOW_TITLE = "Bouncing Ball";
    // ====================== constant sizes ======================
    private static final float BORDER_WIDTH = 20;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 40;

    private static final int ZERO = 0;
    private static final int COLUMNS_OF_BRICKS = 7;
    private static final int ROWS_OF_BRICKS = 8;
    private static final float HALF = 0.5f;
    private static final int NUM_OF_LIVES = 3;
    private static final int WIDGETS_LEN = 4 * Constants.WIDGET_SIZE;
    private static final int TWO = 2;
    private static final int PADDLE_DIST_FROM_BOTTOM = 30;
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;
    // ====================== private fields ======================
    private UserInputListener inputListener;
    private Ball mainBall;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private SoundReader soundReader;
    private Counter gamesCounter;
    private Counter brickCounter;
    private Counter paddlesCounter;
    private final CollisionStrategyFactory collisionStrategyFactory;
    private GraphicLifeCounter graphicLifeCounter;

    public BrickerGameManager(
        String windowTitle, Vector2 windowDimensions, CollisionStrategyFactory collisionStrategyFactory) {
        super(windowTitle, windowDimensions);
        this.collisionStrategyFactory = collisionStrategyFactory;
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
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        gamesCounter = new Counter(NUM_OF_LIVES);
        brickCounter = new Counter(ROWS_OF_BRICKS * COLUMNS_OF_BRICKS);
        paddlesCounter = new Counter(ZERO);
        windowDimensions = windowController.getWindowDimensions();

        //create background
        createBackground(imageReader);

        //create ball
        createBall(imageReader, soundReader, windowController);

        //create paddle
        Renderable paddleImage = imageReader.readImage(
            Constants.ASSETS_PADDLE_PNG, false);
        createPaddle(paddleImage, inputListener, windowDimensions);

        //create borders
        createBorders(windowDimensions);

        // create graphic life counter
        Renderable lifeImage = imageReader.readImage(
            Constants.ASSETS_HEART_PNG, true);
        Vector2 lifeImageLocation = new Vector2(0, windowDimensions.y());
        graphicLifeCounter = new GraphicLifeCounter(lifeImageLocation, Vector2.ZERO,
            gamesCounter, lifeImage, gameObjects(), NUM_OF_LIVES);
        gameObjects().addGameObject(graphicLifeCounter, Layer.UI);

        // create numeric life counter
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(gamesCounter,
            new Vector2(BORDER_WIDTH + WIDGETS_LEN, (windowDimensions.y() - Constants.WIDGET_SIZE)),
            new Vector2(Constants.WIDGET_SIZE, Constants.WIDGET_SIZE),
            gameObjects());
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);

        //create bricks
        createBricks(imageReader);
    }

    private void createBackground(ImageReader imageReader) {
        Renderable background = imageReader.readImage(Constants.ASSETS_BACKGROUND, false);
        gameObjects().addGameObject(
            new GameObject(Vector2.ZERO, windowDimensions, background), Layer.BACKGROUND);
    }

    private void createBricks(ImageReader imageReader) {
        float brickWidth = (int) ((windowDimensions.x() - TWO * BORDER_WIDTH) / COLUMNS_OF_BRICKS);
        float brickHeight = (int) ((windowDimensions.y()) / (NUM_OF_LIVES * ROWS_OF_BRICKS));
        for (int i = ZERO; i < ROWS_OF_BRICKS; i++) {
            for (int j = ZERO; j < COLUMNS_OF_BRICKS; j++) {
                int brickX = (int) (BORDER_WIDTH + brickWidth * j + brickWidth * HALF);
                int brickY = (int) (brickHeight * i + brickHeight * HALF);
                Vector2 brickPosition = new Vector2(brickX, brickY);
                Vector2 brickDimensions = new Vector2(brickWidth, brickHeight);
                Brick brick = createSingleBrick(imageReader, brickDimensions, brickPosition);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private Brick createSingleBrick(ImageReader imageReader, Vector2 brickDimensions, Vector2 brickPosition) {
        CollisionStrategy strategy = collisionStrategyFactory.create(gameObjects(), brickDimensions, brickPosition, imageReader,
            soundReader, inputListener, windowDimensions, paddlesCounter,
            gamesCounter, graphicLifeCounter, this, mainBall, windowController);

        Renderable brickImage = imageReader.readImage(Constants.ASSETS_BRICK_PNG, false);
        var brick = new Brick(Vector2.ZERO, brickDimensions,
            brickImage, strategy, gameObjects(), brickCounter);
        brick.setCenter(brickPosition);

        return brick;
    }

    private void createBall(
        ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
        Renderable ballImage =
            imageReader.readImage(Constants.ASSETS_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(Constants.ASSETS_BLOP_WAV);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        mainBall = new Ball(
            Vector2.ZERO,
            new Vector2(BALL_RADIUS, BALL_RADIUS),
            windowDimensions.mult(HALF),
            ballImage,
            collisionSound
        );

        gameObjects().addGameObject(mainBall);

        mainBall.setRandomVelocity();
    }

    private void createPaddle(
        Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions) {
        GameObject Paddle = new Paddle(
            Vector2.ZERO,
            new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
            paddleImage,
            inputListener,
            windowDimensions,
            Constants.MIN_DIST_FROM_EDGE,
            gameObjects(),
            true);

        Paddle.setCenter(new Vector2(
            windowDimensions.x() / TWO,
            (int) windowDimensions.y() - PADDLE_DIST_FROM_BOTTOM));
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
        double ballHeight = mainBall.getCenter().y();
        if (ballHeight > windowDimensions.y()) {
            gamesCounter.decrement();
            mainBall.setCenter(windowDimensions.mult(HALF));
        }
        checkForGameEnd();
    }

    private void checkForGameEnd() {

        String prompt = PROMPT;
        if (brickCounter.value() <= ZERO) {
            prompt = YOU_WIN;
        }
        if (gamesCounter.value() <= ZERO) {
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
            new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT),
            new CollisionStrategyFactory()
        ).run();
    }
}
