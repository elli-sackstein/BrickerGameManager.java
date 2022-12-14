package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.Constants;

public class GraphicLifeCounter extends GameObject {
    private static final double HALF = 0.5;

    private Vector2 widgetTopLeftCorner;
    private final Counter livesCounter;
    private Renderable widgetRenderable;
    private final GameObjectCollection gameObjectsCollection;
    private int numOfLives;
    private GameObject[] gameObjects;
    private final int objLocationX;
    private final int objLocationY;
    /**
     * Construct a new GameObject instance.
     *
     * @param widgetTopLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions Width and height in window coordinates.
     * @param livesCounter Counter objects, holds value of num of lives.
     * @param widgetRenderable The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param gameObjectsCollection the collection of all game objects currently in the game.
     * @param numOfLives initial value of lives number.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Counter livesCounter,
                              Renderable widgetRenderable, GameObjectCollection gameObjectsCollection,
                              int numOfLives) {
        // initialize super and private fields
        super(widgetTopLeftCorner,  widgetDimensions, widgetRenderable);
        this.widgetTopLeftCorner = widgetTopLeftCorner;
        this.livesCounter = livesCounter;
        this.widgetRenderable = widgetRenderable;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;

        // calculates the center of the first heart
        objLocationX = (int)(widgetTopLeftCorner.x() + HALF * Constants.WIDGET_SIZE);
        objLocationY = (int)(widgetTopLeftCorner.y() - HALF * Constants.WIDGET_SIZE);

        // creates the private array of hearts and places them by their center
        createGameObjectsArray(numOfLives + 1);
        for (int i = 0; i < numOfLives; i++) {
            gameObjects[i] = new GameObject(
                    widgetTopLeftCorner, new Vector2(Constants.WIDGET_SIZE, Constants.WIDGET_SIZE), widgetRenderable);
            gameObjects[i].setCenter(new Vector2(objLocationX + i * Constants.WIDGET_SIZE, objLocationY));
            gameObjectsCollection.addGameObject(gameObjects[i], Layer.UI);
        }
    }

    @Override
    public void update(float deltaTime) {
        // updates the hearts according to counter
        int lastIndex = numOfLives - 1;
        super.update(deltaTime);

        if (livesCounter.value() < numOfLives){
            gameObjectsCollection.removeGameObject(gameObjects[lastIndex], Layer.UI);
            numOfLives--;
        }
    }

    public void onHeartCollision(Heart heart){
        if (livesCounter.value() < 4){
            livesCounter.increment();
            heart.setVelocity(Vector2.ZERO);
            gameObjectsCollection.removeGameObject(heart);
            placeCollectedHeart();
        }
    }

    private void placeCollectedHeart(){
        GameObject heart = new GameObject(
            widgetTopLeftCorner, new Vector2(Constants.WIDGET_SIZE, Constants.WIDGET_SIZE), widgetRenderable);
        gameObjects[livesCounter.value()-1] = heart;
        heart.setCenter(new Vector2(objLocationX + numOfLives * Constants.WIDGET_SIZE, objLocationY));
        gameObjectsCollection.addGameObject(heart, Layer.UI);
        numOfLives++;
    }

    private void createGameObjectsArray(int numOfLives) {
        // creates the private array of hearts
        gameObjects = new GameObject[numOfLives];
    }
}
