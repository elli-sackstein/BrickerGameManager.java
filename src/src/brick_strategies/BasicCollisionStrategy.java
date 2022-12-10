package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjects;

    public BasicCollisionStrategy(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter, boolean remove) {
        gameObjects.removeGameObject(collidedObj, Layer.STATIC_OBJECTS);
        bricksCounter.decrement();
    }
}
