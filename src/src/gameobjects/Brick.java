package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {
    private final CollisionStrategy strategy;
    private final Counter counter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param strategy  The strategy that will be used when the brick breaks.
     * @param counter  A counter.
     */
    public Brick(
            Vector2 topLeftCorner,
            Vector2 dimensions,
            Renderable renderable,
            CollisionStrategy strategy,
            Counter counter) {
        super(topLeftCorner, dimensions, renderable);

        this.strategy = strategy;
        this.counter = counter;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        strategy.onCollision(this, other, counter);
    }
}
