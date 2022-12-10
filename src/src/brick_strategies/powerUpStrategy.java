package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class powerUpStrategy extends BasicCollisionStrategy{
    private Counter powerUpCounter;
    private CollisionStrategyFactory factory;
    private CollisionStrategy[] strategies;
    private int strategiesIndex;
    public powerUpStrategy(GameObjectCollection gameObjects, CollisionStrategyFactory factory,
                           Counter powerUpCounter) {
        super(gameObjects);
        this.factory = factory;
        this.powerUpCounter = powerUpCounter;
        this.strategies = new CollisionStrategy[powerUpCounter.value()];
        this.strategiesIndex = 0;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        createCollisionArray();
        for (int i = 0; i < strategies.length; i++) {
            System.out.println("got here, strategy: " + strategies[i].getClass());
            strategies[i].onCollision(collidedObj, colliderObj, bricksCounter);
        }
        // the counter decreases 3 times instead of 1
        bricksCounter.increaseBy(2);
    }

    private void createCollisionArray() {
        while (powerUpCounter.value() > 0) {
            if (powerUpCounter.value() == 1) {
                int rand = factory.getRandomNumberUsingNextInt(0, 4);
                CollisionStrategy strategy = factory.chooseStrategy(rand);
                strategies[strategiesIndex] = strategy;
            } else {
                int rand = factory.getRandomNumberUsingNextInt(1, 5);
                CollisionStrategy strategy = factory.chooseStrategy(rand);
                if (strategy.getClass() != this.getClass()) {
                    strategies[strategiesIndex] = strategy;
                    strategiesIndex++;
                } else {
                    System.out.println();
                }
            }
            powerUpCounter.decrement();
        }
    }
}
