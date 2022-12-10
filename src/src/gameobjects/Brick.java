package src.gameobjects;

import danogl.collisions.*;
import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {
    private final CollisionStrategy strategy;
    private GameObjectCollection gameObjects;
    private final Counter counter;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
 *                      the GameObject will not be rendered.
     * @param strategy  The strategy that will be used when the brick breaks.
     * @param gameObjects
     * @param counter  A counter.
     */
    public Brick(
        Vector2 topLeftCorner,
        Vector2 dimensions,
        Renderable renderable,
        CollisionStrategy strategy,
        GameObjectCollection gameObjects,
        Counter counter) {
        super(topLeftCorner, dimensions, renderable);

        this.strategy = strategy;
        this.gameObjects = gameObjects;
        this.counter = counter;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        gameObjects.removeGameObject(this, Layer.STATIC_OBJECTS);
        counter.decrement();

        strategy.onCollision(this, other, counter);
    }
}
