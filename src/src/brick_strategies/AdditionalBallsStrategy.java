package src.brick_strategies;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.*;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;

import static src.BrickerGameManager.*;
//TODO: fix the balls layer
public class AdditionalBallsStrategy extends BasicCollisionStrategy {
    // ====================== constants ======================
    public static final String ASSETS_BALL_PNG = "assets/mockBall.png";
    // ====================== fields ======================
    private final Vector2 brickDimensions;
    private final Vector2 brickPosition;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private GameManager gameManager;

    public AdditionalBallsStrategy(GameObjectCollection gameObjects, Vector2 brickDimensions,
                                   Vector2 brickPosition, ImageReader imageReader, SoundReader soundReader,
                                   GameManager gameManager) {
        super(gameObjects);
        this.brickDimensions = brickDimensions;
        this.brickPosition = brickPosition;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter, boolean remove) {
        if (remove) {
            super.onCollision(collidedObj, colliderObj, bricksCounter, false);
        }
        for (int i = 0; i < 3; i++) {
            createBall(i);
        }
    }

    private void createBall(int index) {
        Renderable ballImage = imageReader.readImage(ASSETS_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(ASSETS_BLOP_WAV);

        float radius = brickDimensions.x()/3;
        float posY = brickPosition.y();
        float posX = brickPosition.x() + (index-1) * radius;

        Ball ball = new Ball(
                Vector2.ZERO,
                new Vector2(radius, radius),
                new Vector2(posX, posY),
                ballImage,
                collisionSound,
                false,
                gameManager
        );
        // TODO:why not in the constructor?
        ball.setRandomVelocity();

        gameObjects.addGameObject(ball, Layer.STATIC_OBJECTS);
    }
}
