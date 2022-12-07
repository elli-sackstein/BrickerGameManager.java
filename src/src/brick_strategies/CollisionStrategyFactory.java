package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.util.Vector2;

public class CollisionStrategyFactory {

    public CollisionStrategy create(
        GameObjectCollection gameObjects, Vector2 brickDimensions,
        Vector2 brickPosition, ImageReader imageReader, SoundReader soundReader,
        WindowController windowController) {

        return new BasicCollisionStrategy(gameObjects);
//        return new AdditionalBallsStrategy(
//            gameObjects,
//            brickDimensions,
//            brickPosition,
//            imageReader,
//            soundReader,
//            windowController);
    }
}
