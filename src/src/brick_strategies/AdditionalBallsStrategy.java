package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;

import java.util.Random;

import static src.BrickerGameManager.*;

public class AdditionalBallsStrategy extends BasicCollisionStrategy {
    // ====================== constants ======================
    public static final String ASSETS_BALL_PNG = "assets/mockBall.png";
    // ====================== fields ======================
    private final Vector2 brickDimensions;
    private final Vector2 brickPosition;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final WindowController windowController;
    private final GameObjectCollection gameObjects;

    public AdditionalBallsStrategy(GameObjectCollection gameObjects, Vector2 brickDimensions,
                                   Vector2 brickPosition, ImageReader imageReader, SoundReader soundReader,
                                   WindowController windowController) {
        super(gameObjects);
        this.gameObjects = gameObjects;
        this.brickDimensions = brickDimensions;
        this.brickPosition = brickPosition;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowController = windowController;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
        for (int i = 0; i < 3; i++) {
            createBall(i);
        }
    }

    // duplicated code from BrickGameManager
    private void createBall(int index) {
        Renderable ballImage =
                imageReader.readImage(ASSETS_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(ASSETS_BLOP_WAV);
        Ball ball = new Ball(
                Vector2.ZERO, new Vector2(brickDimensions.x()/3, brickDimensions.y()/3), ballImage,
                collisionSound);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(brickPosition);
        gameObjects.addGameObject(ball);

        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= OPPOSITE_DIRECTION;
        if (rand.nextBoolean())
            ballVelY *= OPPOSITE_DIRECTION;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }
}
