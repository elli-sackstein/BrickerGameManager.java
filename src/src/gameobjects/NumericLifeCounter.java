package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {
    public static final int TWO_LIVES = 2;
    public static final int ONE_LIFE = 1;
    private final TextRenderable textRenderable;
    private final Counter livesCounter;
    /**
     * Construct a new GameObject instance.
     * @param livesCounter  Counter objects, holds value of num of lives.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     *                      (topLeftCorner of the widget location).
     * @param dimensions    Width and height in window coordinates.
     * @param gameObjectCollection the collection of all game objects currently in the game.
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {

        // initialize super and private fields
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;

        //initialize textRenderable and place it
        this.textRenderable = new TextRenderable(Integer.toString(livesCounter.value()));
        textRenderable.setColor(Color.GREEN);
        this.setCenter(topLeftCorner);
        GameObject textLives = new GameObject(topLeftCorner, dimensions, textRenderable);
        gameObjectCollection.addGameObject(textLives, Layer.UI);
    }

    @Override
    // this method updates the number of lives left, and changes colors accordingly.
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (livesCounter.value() == TWO_LIVES)
        {
            textRenderable.setColor(Color.YELLOW);
            textRenderable.setString(Integer.toString(livesCounter.value()));
        }
        else if (livesCounter.value() == ONE_LIFE)
        {
            textRenderable.setColor(Color.RED);
            textRenderable.setString(Integer.toString(livesCounter.value()));
        }
    }
}
