package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.*;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.*;
import src.gameobjects.*;

import static src.BrickerGameManager.ASSETS_BLOP_WAV;

public class AdditionalDiskStrategy extends BasicCollisionStrategy {
    // ====================== constants ======================
    public static final String DISK_PNG = "assets/botGood.png";
    // ====================== fields ======================
    private final Vector2 brickDimensions;
    private final Vector2 brickPosition;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private UserInputListener inputListener;

    public AdditionalDiskStrategy(
        GameObjectCollection gameObjects, Vector2 brickDimensions,
        Vector2 brickPosition, ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener) {
        super(gameObjects);
        this.brickDimensions = brickDimensions;
        this.brickPosition = brickPosition;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);

//        Renderable paddleImage = imageReader.readImage(
//            DISK_PNG, false);
//        new createPaddle(paddleImage, inputListener)
    }

//    private void createPaddle(
//        Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions) {
//        GameObject Paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
//            paddleImage, inputListener, windowDimensions, MIN_DIST_FROM_EDGE);
//
//        Paddle.setCenter(new Vector2(
//            windowDimensions.x() / TWO, (int) windowDimensions.y() - PADDLE_DIST_FROM_BOTTOM));
//        gameObjects().addGameObject(Paddle);
//    }
}
