package javatetris;

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Dimension2D;

import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.image.Image;

import javatetris.game.GameArea;
import javatetris.internal.Drawable;
import javatetris.internal.GameObject;
import javatetris.internal.Simulatable;
import javatetris.ui.Graphic;
import javatetris.ui.HBox;
import javatetris.ui.VBox;
import lombok.Getter;

public class Scene {

    final Dimension2D dim;
    @Getter
    ArrayList<GameObject> objects = new ArrayList<>();

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

    public Scene(Dimension2D dim) {
        this.dim = dim;

        var hbox = new HBox();
        hbox.addChild(
            new Graphic(new Image(Objects
                                      .requireNonNull(getClass().getResource(
                                          "/javatetris/textures/ui/UILeft.png"))
                                      .toExternalForm())));
        hbox.addChild(new GameArea());
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

    public void simulate(float deltaTime) {
        for (var obj : objects) {
            if (obj instanceof Simulatable simulatable) {
                simulatable.simulate(deltaTime);
            }
        }
    }
}
