package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class CollisionStrategy{
    private final GameObjectCollection gameObjects;

    public CollisionStrategy(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;
    }

    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter){
        gameObjects.removeGameObject(collidedObj, Layer.STATIC_OBJECTS);
        bricksCounter.decrement();
    }

}
