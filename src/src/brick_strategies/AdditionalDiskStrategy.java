package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.*;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.*;
import src.Constants;
import src.gameobjects.*;

public class AdditionalDiskStrategy implements CollisionStrategy {
    // ====================== fields ======================
    private final GameObjectCollection gameObjects;
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
        Renderable paddleImage = imageReader.readImage(Constants.DISK_PNG, false);
        Paddle paddle = new Paddle(Vector2.ZERO, brickDimensions, paddleImage, inputListener, windowDimensions,
            Constants.MIN_DIST_FROM_EDGE, gameObjects, false);

        paddle.setCenter(new Vector2(
                windowDimensions.x() / 2, (int) (windowDimensions.y() / 2)));
        gameObjects.addGameObject(paddle, Layer.STATIC_OBJECTS);
    }
}
