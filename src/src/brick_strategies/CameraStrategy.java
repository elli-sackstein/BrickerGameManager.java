package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCounter;

public class CameraStrategy extends BasicCollisionStrategy{

    private GameManager gameManager;
    private Ball ball;
    private WindowController windowController;

    public CameraStrategy(GameObjectCollection gameObjects, GameManager gameManager, Ball ball,
                          WindowController windowController) {
        super(gameObjects);
        this.gameManager = gameManager;
        this.ball = ball;
        this.windowController = windowController;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        super.onCollision(collidedObj, colliderObj, bricksCounter);
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
