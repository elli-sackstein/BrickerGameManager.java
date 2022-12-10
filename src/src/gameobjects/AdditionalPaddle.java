package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.*;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.*;

public class AdditionalPaddle extends Paddle {
    private final Counter counter;
    private final GameObjectCollection gameObjects;

    public AdditionalPaddle(Vector2 topLeftCorner,
                            Vector2 dimensions,
                            Renderable renderable,
                            UserInputListener inputListener,
                            Vector2 windowDimensions,
                            int minDistFromEdge,
                            GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistFromEdge);
        this.gameObjects = gameObjects;
        this.counter = new Counter(0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        counter.increment();
        if (counter.value() == 3) {
            gameObjects.removeGameObject(this, Layer.STATIC_OBJECTS);
        }
    }
}
