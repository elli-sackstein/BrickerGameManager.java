package src.brick_strategies;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.GraphicLifeCounter;

import java.util.Random;

public class CollisionStrategyFactory {
    private Counter powerUpCounter;
    private final static int NUM_OF_STRATEGY = 6;
    private GameObjectCollection gameObjects;
    private Vector2 brickDimensions;
    private Vector2 brickPosition;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private Counter paddlesCounter;
    private Counter collisionsCounter;
    private Counter livesCounter;
    private GraphicLifeCounter graphicLifeCounter;
    private GameManager gameManager;
    private Ball ball;
    private WindowController windowController;

    public CollisionStrategy create(
            GameObjectCollection gameObjects, Vector2 brickDimensions,
            Vector2 brickPosition, ImageReader imageReader, SoundReader soundReader,
            UserInputListener inputListener, Vector2 windowDimensions, Counter paddlesCounter,
            Counter collisionsCounter, Counter livesCounter, GraphicLifeCounter graphicLifeCounter,
            GameManager gameManager, Ball ball, WindowController windowController) {
        this.gameObjects = gameObjects;
        this.brickDimensions = brickDimensions;
        this.brickPosition = brickPosition;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddlesCounter = paddlesCounter;
        this.collisionsCounter = collisionsCounter;
        this.livesCounter = livesCounter;
        this.graphicLifeCounter = graphicLifeCounter;
        this.gameManager = gameManager;
        this.ball = ball;
        this.windowController = windowController;
        powerUpCounter = new Counter(3);
        int rand = getRandomNumberUsingNextInt(0, NUM_OF_STRATEGY);
        return chooseStrategy(rand);
    }

    public CollisionStrategy chooseStrategy(int strategyIndex){
        switch (strategyIndex){
            case 0:
                return new BasicCollisionStrategy(gameObjects);
            case 1:
                return new AdditionalBallsStrategy(gameObjects, brickDimensions, brickPosition, imageReader,
                        soundReader, gameManager);
            case 2:
                return new AdditionalDiskStrategy(gameObjects, brickDimensions, imageReader, inputListener,
                        windowDimensions, paddlesCounter, collisionsCounter);
            case 3:
                return new CameraStrategy(gameObjects, gameManager, ball, windowController);
            case 4:
                return new AddLifeStrategy(gameObjects, imageReader, brickPosition, windowDimensions,
                        livesCounter, graphicLifeCounter);
            case 5:
                return new powerUpStrategy(gameObjects,this, powerUpCounter);
        }
        return new BasicCollisionStrategy(gameObjects);
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
