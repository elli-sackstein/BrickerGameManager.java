package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Heart extends GameObject {
    private static final float HEART_SPEED = 100;

    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;
    private final GraphicLifeCounter graphicLifeCounter;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Vector2 position,
                 Vector2 windowDimensions, GameObjectCollection gameObjects,
                 GraphicLifeCounter graphicLifeCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.windowDimensions = windowDimensions;
        this.gameObjects = gameObjects;
        this.graphicLifeCounter = graphicLifeCounter;
        setCenter(position);
        setVelocity(new Vector2(0, HEART_SPEED));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getCenter().y() > windowDimensions.y()){
            gameObjects.removeGameObject(this);
        }
    }
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle && !(other instanceof AdditionalPaddle);
    }
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        graphicLifeCounter.onHeartCollision(this);
    }
}
