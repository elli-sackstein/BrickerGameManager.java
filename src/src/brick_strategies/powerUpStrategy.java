package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.*;

public class powerUpStrategy extends BasicCollisionStrategy{
    private Vector2 brickPosition;
    private Counter powerUpCounter;
    private CollisionStrategyFactory factory;
    private CollisionStrategy[] strategies;
    private int strategiesIndex;
    public powerUpStrategy(GameObjectCollection gameObjects, CollisionStrategyFactory factory,
                           Vector2 brickPosition, Counter powerUpCounter) {
        super(gameObjects);
        this.factory = factory;
        this.brickPosition = brickPosition;
        this.powerUpCounter = powerUpCounter;
        this.strategies = new CollisionStrategy[powerUpCounter.value()];
        this.strategiesIndex = 0;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter, boolean remove) {
        createCollisionArray();
        for (int i = 0; i < strategies.length; i++) {
            strategies[i].onCollision(collidedObj, colliderObj, bricksCounter, i == strategies.length - 1);
        }
        // the counter decreases 3 times instead of 1
        bricksCounter.increaseBy(2);
    }

    private void createCollisionArray() {
        while (powerUpCounter.value() > 0) {
            int rand = getRand(powerUpCounter.value());
            createStrategy(rand);
            powerUpCounter.decrement();
        }
    }

    private int getRand(int value) {
        if (value == 1) {
            return factory.getRandomNumberUsingNextInt(0, 5);
        }
        return factory.getRandomNumberUsingNextInt(1, 5);
    }

    private void createStrategy(int rand) {
        CollisionStrategy strategy = factory.chooseStrategy(rand, this.brickPosition);
        strategies[strategiesIndex] = strategy;
        strategiesIndex++;
    }
}
