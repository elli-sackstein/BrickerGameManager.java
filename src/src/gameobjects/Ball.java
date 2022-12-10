package src.gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class Ball extends GameObject {

    private static final float BALL_SPEED = 100;
    private static final int OPPOSITE_DIRECTION = -1;

    private final Sound collisionSound;
    private final Boolean mainBall;
    private final Counter collisionsCounter;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param collisionSound The collision sound.
     */
    public Ball(
            Vector2 topLeftCorner,
            Vector2 dimensions,
            Vector2 position,
            Renderable renderable,
            Sound collisionSound,
            Boolean mainBall,
            GameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.mainBall = mainBall;
        setCenter(position);
        collisionsCounter = new Counter(0);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        if (mainBall){
            collisionsCounter.increment();
        }
    }

    public void setRandomVelocity(){
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= OPPOSITE_DIRECTION;
        if (rand.nextBoolean())
            ballVelY *= OPPOSITE_DIRECTION;
        setVelocity(new Vector2(ballVelX, ballVelY));
    }

    public Counter getCollisionCounter(){
        return collisionsCounter;
    }
}
