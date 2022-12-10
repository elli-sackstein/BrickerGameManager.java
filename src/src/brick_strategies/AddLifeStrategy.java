package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.Heart;

import static src.BrickerGameManager.WIDGET_SIZE;

public class AddLifeStrategy extends BasicCollisionStrategy{
    public static final String ASSETS_HEART_PNG = "assets/heart.png";
    private final ImageReader imageReader;
    private final Vector2 position;
    private final Vector2 windowDimensions;
    private Counter livesCounter;
    private GraphicLifeCounter graphicLifeCounter;

    public AddLifeStrategy(GameObjectCollection gameObjects, ImageReader imageReader, Vector2 position,
                           Vector2 windowDimensions, Counter livesCounter,
                           GraphicLifeCounter graphicLifeCounter) {
        super(gameObjects);
        this.imageReader = imageReader;
        this.position = position;
        this.windowDimensions = windowDimensions;
        this.livesCounter = livesCounter;
        this.graphicLifeCounter = graphicLifeCounter;
    }
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter, boolean remove) {
        if (remove) {
            super.onCollision(collidedObj, colliderObj, bricksCounter, false);
        }
        System.out.printf("Addlife position %s\n", this.position.toString());

        createHeart();
    }

    private void createHeart() {
        Renderable lifeImage = imageReader.readImage(ASSETS_HEART_PNG, true);
        Heart heart = new Heart(Vector2.ZERO, new Vector2(WIDGET_SIZE, WIDGET_SIZE), lifeImage, position,
                windowDimensions, gameObjects, livesCounter, graphicLifeCounter);
        gameObjects.addGameObject(heart);
    }

}
