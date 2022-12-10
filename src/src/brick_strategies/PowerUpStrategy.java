package src.brick_strategies;

import danogl.GameObject;
import danogl.util.*;

public class PowerUpStrategy implements CollisionStrategy{
    private final Vector2 brickPosition;
    private final Counter powerUpCounter;
    private final CollisionStrategyFactory factory;

    private int strategiesIndex;

    public PowerUpStrategy(CollisionStrategyFactory factory,
                           Vector2 brickPosition, Counter powerUpCounter) {
        this.factory = factory;
        this.brickPosition = brickPosition;
        this.powerUpCounter = powerUpCounter;
        this.strategiesIndex = 0;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        var strategies = createCollisionArray();

        for (CollisionStrategy strategy : strategies) {
            strategy.onCollision(collidedObj, colliderObj, bricksCounter);
        }
    }

    private CollisionStrategy[] createCollisionArray() {
        var strategies = new CollisionStrategy[powerUpCounter.value()];

        while (powerUpCounter.value() > 0) {
            int rand = getRand(powerUpCounter.value());
            strategies[strategiesIndex] = factory.chooseStrategy(rand, this.brickPosition);
            strategiesIndex++;
            powerUpCounter.decrement();
        }

        return strategies;
    }

    private int getRand(int value) {
        if (value == 1) {
            return factory.getRandomNumberUsingNextInt(0, 5);
        }
        // TODO: Add justification
        return factory.getRandomNumberUsingNextInt(1, 5);
    }
}
