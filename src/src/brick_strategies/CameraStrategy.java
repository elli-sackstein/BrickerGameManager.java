package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.*;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCounter;

public class CameraStrategy implements CollisionStrategy {

    private final GameObjectCollection gameObjects;
    private final GameManager gameManager;
    private final Ball mainBall;
    private final WindowController windowController;

    public CameraStrategy(GameObjectCollection gameObjects, GameManager gameManager, Ball mainBall,
                          WindowController windowController) {
        this.gameObjects = gameObjects;
        this.gameManager = gameManager;
        this.mainBall = mainBall;
        this.windowController = windowController;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {

        boolean shouldTurnCameraOn = gameManager.getCamera() == null && (colliderObj instanceof Ball);
        if (!shouldTurnCameraOn) {
            return;
        }

        Vector2 windowDimensions = windowController.getWindowDimensions();

        Camera camera = new Camera(
            mainBall,
            Vector2.ZERO,
            windowDimensions.mult(1.2f),
            windowDimensions);

        gameManager.setCamera(camera);

        BallCollisionCounter ballCollisionCounter = new BallCollisionCounter(
            mainBall.getCollisionCounter(),
            gameManager,
            gameObjects);

        gameObjects.addGameObject(ballCollisionCounter);
    }
}
