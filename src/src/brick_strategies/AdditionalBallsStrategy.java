package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.*;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.Constants;
import src.gameobjects.Ball;

//TODO: fix the balls layer
public class AdditionalBallsStrategy implements CollisionStrategy {
    // ====================== fields ======================
    private final GameObjectCollection gameObjects;
    private final Vector2 brickDimensions;
    private final Vector2 brickPosition;
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    public AdditionalBallsStrategy(GameObjectCollection gameObjects, Vector2 brickDimensions,
                                   Vector2 brickPosition, ImageReader imageReader, SoundReader soundReader) {
        this.gameObjects = gameObjects;
        this.brickDimensions = brickDimensions;
        this.brickPosition = brickPosition;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj, Counter bricksCounter) {
        for (int i = 0; i < 3; i++) {
            createBall(i);
        }
    }

    private void createBall(int index) {
        Renderable ballImage = imageReader.readImage(Constants.ASSETS_PUCK_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(Constants.ASSETS_BLOP_WAV);

        float radius = brickDimensions.x()/3;
        float posY = brickPosition.y();
        float posX = brickPosition.x() + (index-1) * radius;

        Ball ball = new Ball(
                Vector2.ZERO,
                new Vector2(radius, radius),
                new Vector2(posX, posY),
                ballImage,
                collisionSound
        );
        // TODO:why not in the constructor?
        ball.setRandomVelocity();

        gameObjects.addGameObject(ball);
    }
}
