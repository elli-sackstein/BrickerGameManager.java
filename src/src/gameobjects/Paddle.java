package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final int MOVE_SPEED = 500;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDistFromEdge;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener holds a sound
     * @param windowDimensions the window dimensions
     * @param minDistFromEdge the minimum distance allowed from edges.
     */
    public Paddle(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  UserInputListener inputListener ,
                  Vector2 windowDimensions,
                  int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        // checks if user is pressing left button
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            // if the paddle reaches the minimum distance allowed from edges, it stops.
            if (getTopLeftCorner().x() > minDistFromEdge){
                movementDir = movementDir.add(Vector2.LEFT);
            }
        }
        // checks if user is pressing right button
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            // if the paddle reaches the minimum distance allowed from edges, it stops.
            if (windowDimensions.x() - minDistFromEdge > getTopLeftCorner().x() + getDimensions().x()){
                movementDir = movementDir.add(Vector2.RIGHT);
            }
        }
        // multiples the direction vector by the speed
        setVelocity(movementDir.mult(MOVE_SPEED));
    }
}
