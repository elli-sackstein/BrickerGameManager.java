package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.*;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.*;
import src.gameobjects.*;

import static src.BrickerGameManager.ASSETS_BLOP_WAV;

public class AdditionalDiskStrategy implements CollisionStrategy {
    // ====================== constants ======================
    public static final String DISK_PNG = "assets/botGood.png";
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    public static final int MIN_DIST_FROM_EDGE = 15;
    public static final int TWO = 2;
    private final GameObjectCollection gameObjects;
    // ====================== fields ======================
    private final Vector2 brickDimensions;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final Counter paddlesCounter;

    public AdditionalDiskStrategy(
            GameObjectCollection gameObjects, Vector2 brickDimensions, ImageReader imageReader,
            UserInputListener inputListener, Vector2 windowDimensions, Counter paddlesCounter) {
        this.gameObjects = gameObjects;
        this.brickDimensions = brickDimensions;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.paddlesCounter = paddlesCounter;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        if (paddlesCounter.value() == 0){
            createPaddle();
            paddlesCounter.increment();
        }
    }

    private void createPaddle() {
        Renderable paddleImage = imageReader.readImage(DISK_PNG, false);
        Paddle paddle = new Paddle(Vector2.ZERO, brickDimensions, paddleImage, inputListener, windowDimensions,
            MIN_DIST_FROM_EDGE, this, gameObjects, false);

        paddle.setCenter(new Vector2(
                windowDimensions.x() / TWO, (int) (windowDimensions.y() / TWO)));
        gameObjects.addGameObject(paddle, Layer.STATIC_OBJECTS);
    }
}
