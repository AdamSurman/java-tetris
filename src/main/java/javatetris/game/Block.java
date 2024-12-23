package javatetris.game;

import java.util.Objects;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import javatetris.internal.Drawable;
import javatetris.ui.Graphic;

public class Block extends Graphic implements Drawable {
  public enum Color {
    BLUE,
    CYAN,
    GREEN,
    GREY,
    MAGENTA,
    ORANGE,
    RED,
    YELLOW;

    public Image getTexture() {
      return switch (this) {
                case BLUE -> new Image(Objects.requireNonNull(getClass().getResource("/javatetris/textures/blocks/BlockBlue.png")).toExternalForm());
                case CYAN -> new Image(Objects.requireNonNull(getClass().getResource("/javatetris/textures/blocks/BlockCyan.png")).toExternalForm());
                case GREEN -> new Image(Objects.requireNonNull(getClass().getResource("/javatetris/textures/blocks/BlockGreen.png")).toExternalForm());
                case GREY -> new Image(Objects.requireNonNull(getClass().getResource("/javatetris/textures/blocks/BlockGrey.png")).toExternalForm());
                case MAGENTA -> new Image(Objects.requireNonNull(getClass().getResource("/javatetris/textures/blocks/BlockMagenta.png")).toExternalForm());
                case ORANGE -> new Image(Objects.requireNonNull(getClass().getResource("/javatetris/textures/blocks/BlockOrange.png")).toExternalForm());
                case RED -> new Image(Objects.requireNonNull(getClass().getResource("/javatetris/textures/blocks/BlockRed.png")).toExternalForm());
                case YELLOW -> new Image(Objects.requireNonNull(getClass().getResource("/javatetris/textures/blocks/BlockYellow.png")).toExternalForm());
            };
        }
    }

    public Block(Dimension2D position, Color color) {
        super(position, color.getTexture());
    }

    Block(Block template) {
        super(new Dimension2D(template.getPosition().getWidth(), template.getPosition().getHeight()), template.texture);
    }

    public Block copy() {
      return new Block(this);
    }
}
