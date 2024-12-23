package javatetris;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Dimension2D;

import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.image.Image;

import javatetris.game.Engine;
import javatetris.game.GameArea;
import javatetris.internal.Drawable;
import javatetris.internal.GameController;
import javatetris.internal.GameObject;
import javatetris.ui.Graphic;
import javatetris.ui.HBox;
import javatetris.ui.VBox;
import lombok.Getter;
import lombok.Setter;

public class Scene {

    final GameController gameController;
    final Dimension2D dim;
    @Getter
    ArrayList<GameObject> objects = new ArrayList<>();
    @Getter
    GameArea gameArea;
    @Getter
    Engine engine;
    @Getter @Setter
    long score = 0;

    /*
     * ┌─ 350 * 350 ──────────────────┐
     * │ 350 * 15                     │
     * ├─┬───────────┬─┬────────────┬─┤
     * │1│ 160 * 320 │1│ 145 * 320  │1│
     * │5│           │5│            │5│
     * │*│ Game area │*│ Next block │*│
     * │3│           │3│ and Score  │3│
     * │2│           │2│            │2│
     * │0│           │0│            │0│
     * │ │           │ │            │ │
     * │ │           │ │            │ │
     * │ │           │ │            │ │
     * ├─┴───────────┴─┴────────────┴─┤
     * │350 * 15                      │
     * └──────────────────────────────┘
     */

    public Scene(Dimension2D dim, GameController gameController) {
        this.gameController = gameController;
        this.dim = dim;
        gameArea = new GameArea();
        engine = new Engine(this);

        var hbox = new HBox();
        hbox.addChild(
            new Graphic(new Image(Objects
                                      .requireNonNull(getClass().getResource(
                                          "/javatetris/textures/ui/UILeft.png"))
                                      .toExternalForm())));
        hbox.addChild(gameArea);
        hbox.addChild(
            new Graphic(new Image(Objects
                                      .requireNonNull(getClass().getResource(
                                          "/javatetris/textures/ui/UIMid.png"))
                                      .toExternalForm())));
        hbox.addChild(
            new Graphic(new Image(Objects
                                      .requireNonNull(getClass().getResource(
                                          "/javatetris/textures/ui/TODO.png"))
                                      .toExternalForm())));
        hbox.addChild(new Graphic(
            new Image(Objects
                          .requireNonNull(getClass().getResource(
                              "/javatetris/textures/ui/UIRight.png"))
                          .toExternalForm())));

        var vbox = new VBox();
        vbox.addChild(
            new Graphic(new Image(Objects
                                      .requireNonNull(getClass().getResource(
                                          "/javatetris/textures/ui/UITop.png"))
                                      .toExternalForm())));
        vbox.addChild(hbox);
        vbox.addChild(
            new Graphic(new Image(Objects
                                      .requireNonNull(getClass().getResource(
                                          "/javatetris/textures/ui/UIBot.png"))
                                      .toExternalForm())));
        objects.add(vbox);
    }

    public void draw(GraphicsContext gc) {
        for (var obj : objects) {
            if (obj instanceof Drawable drawable) {
                drawable.draw(gc);
            }
        }
    }
    float timer = 0.0f;

    public void simulate(float deltaTime) {
        if (timer < 1.0f)
            timer += deltaTime;
        else {
            timer = 0.0f;
            engine.tetrominoTryMoveDown();
        }
        if (engine.checkHeapRows())
            stop();
    }

    void stop() {
        System.out.println("Final score is: " + score);
        Platform.exit();
    }
}
