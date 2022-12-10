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

public class CameraStrategy implements CollisionStrategy{

    private final GameObjectCollection gameObjects;
    private final GameManager gameManager;
    private final Ball ball;
    private final WindowController windowController;

    public CameraStrategy(GameObjectCollection gameObjects, GameManager gameManager, Ball ball,
                          WindowController windowController) {
        this.gameObjects = gameObjects;
        this.gameManager = gameManager;
        this.ball = ball;
        this.windowController = windowController;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter, boolean remove) {

        if ((gameManager.getCamera() == null) && (colliderObj instanceof Ball)){
            gameManager.setCamera(new Camera(ball, Vector2.ZERO,
                    windowController.getWindowDimensions().mult(1.2f),
                    windowController.getWindowDimensions()));

            BallCollisionCounter ballCollisionCounter = new BallCollisionCounter(
                    Vector2.ZERO, Vector2.ZERO, null, gameManager, ball.getCollisionCounter(),
                    gameObjects);
            gameObjects.addGameObject(ballCollisionCounter);
        }

    }
}
