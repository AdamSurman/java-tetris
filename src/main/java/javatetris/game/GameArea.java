package javatetris.game;

import javafx.scene.image.Image;
import javatetris.internal.Simulatable;
import javatetris.ui.Graphic;

import javafx.geometry.Dimension2D;

import java.util.Objects;

public class GameArea extends Graphic implements Simulatable {
    public GameArea(Dimension2D position) {
      super(position, new Image(Objects
                                    .requireNonNull(GameArea.class.getResource(
                                        "/javatetris/textures/ui/GameArea.png"))
                                    .toExternalForm()));
    }
    public GameArea() {
        this(new Dimension2D(0, 0));
    }

    @Override
    public void simulate(float deltaTime) {
        // Game logic here...
    }
}
