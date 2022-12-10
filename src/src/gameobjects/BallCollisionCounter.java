package src.gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class BallCollisionCounter extends GameObject {
    private final GameManager gameManager;
    private final Counter collisionsCounter;
    private final GameObjectCollection gameObjects;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public BallCollisionCounter(
        Counter collisionsCounter,
        GameManager gameManager,
        GameObjectCollection gameObjects) {

        super(Vector2.ZERO, Vector2.ZERO, null);

        this.gameManager = gameManager;
        this.collisionsCounter = collisionsCounter;
        this.gameObjects = gameObjects;

        collisionsCounter.reset();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (collisionsCounter.value() >= 4) {
            gameManager.setCamera(null);
            gameObjects.removeGameObject(this);
        }
    }
}
