package javatetris.internal;

import javafx.animation.AnimationTimer;
import javafx.geometry.Dimension2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javatetris.Scene;
import lombok.Getter;

public class GameThread extends AnimationTimer {

    GraphicsContext gc;

    @Getter
    Scene scene;

    public GameThread(Canvas canvas) {
        this.gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        gc.setFill(Color.WHITE);
        this.scene = new Scene(new Dimension2D(canvas.getWidth(), canvas.getHeight()));
    }

    long previous;

    @Override
    public void handle(long now) {
        final var deltaTime = (now - previous) / 1000f;

        scene.simulate(deltaTime);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        scene.draw(gc);

        previous = now;
    }

}
