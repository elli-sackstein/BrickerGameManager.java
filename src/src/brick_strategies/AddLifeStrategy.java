package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.Constants;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.Heart;

public class AddLifeStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjects;
    private final ImageReader imageReader;
    private final Vector2 position;
    private final Vector2 windowDimensions;
    private final Counter livesCounter;
    private final GraphicLifeCounter graphicLifeCounter;

    public AddLifeStrategy(GameObjectCollection gameObjects, ImageReader imageReader, Vector2 position,
                           Vector2 windowDimensions, Counter livesCounter,
                           GraphicLifeCounter graphicLifeCounter) {
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.position = position;
        this.windowDimensions = windowDimensions;
        this.livesCounter = livesCounter;
        this.graphicLifeCounter = graphicLifeCounter;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        createHeart();
    }

    private void createHeart() {
        Renderable lifeImage = imageReader.readImage(Constants.ASSETS_HEART_PNG, true);
        Heart heart = new Heart(Vector2.ZERO,
            new Vector2(Constants.WIDGET_SIZE, Constants.WIDGET_SIZE),
            lifeImage,
            position,
            windowDimensions, gameObjects, graphicLifeCounter);
        gameObjects.addGameObject(heart);
    }

}
