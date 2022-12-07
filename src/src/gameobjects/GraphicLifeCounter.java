package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {

    public static final double HALF = 0.5;
    public static final int ONE = 1;
    public static final int WIDGET_SIZE = 30;
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectsCollection;
    private int numOfLives;
    private GameObject[] gameObjects;
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
        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;

        // calculates the center of the first heart
        int objLocationX = (int)(widgetTopLeftCorner.x() + HALF * WIDGET_SIZE);
        int objLocationY = (int)(widgetTopLeftCorner.y() - HALF * WIDGET_SIZE);

        // creates the private array of hearts and places them by their center
        createGameObjectsArray(numOfLives);
        for (int i = 0; i < numOfLives; i++) {
            gameObjects[i] = new GameObject(
                    widgetTopLeftCorner, new Vector2(WIDGET_SIZE, WIDGET_SIZE), widgetRenderable);
            gameObjects[i].setCenter(new Vector2(objLocationX + i * WIDGET_SIZE, objLocationY));
            gameObjectsCollection.addGameObject(gameObjects[i], Layer.UI);
        }
    }
    private void createGameObjectsArray(int numOfLives) {
        // creates the private array of hearts
        gameObjects = new GameObject[numOfLives];
    }

    @Override
    public void update(float deltaTime) {
        // updates the hearts according to counter
        int lastIndex = numOfLives - ONE;
        super.update(deltaTime);
        if (livesCounter.value() < numOfLives){
            gameObjectsCollection.removeGameObject(gameObjects[lastIndex], Layer.UI);
            numOfLives--;
        }
    }
}
