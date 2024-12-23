package javatetris.ui;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javatetris.internal.Drawable;
import javatetris.internal.GameObject;

public class Graphic extends GameObject implements Drawable {
    protected final Image texture;

    public Graphic(Dimension2D position, Image texture) {
        super(position, new Dimension2D(texture.getWidth() * 2, texture.getHeight() * 2));
        this.texture = texture;
    }
    public Graphic(Image texture) {
        this(new Dimension2D(0, 0), texture);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(texture, position.getWidth(), position.getHeight(), size.getWidth(), size.getHeight());
        for (var child : getChildren()) {
            if (child instanceof Drawable drawable) {
                drawable.draw(gc);
            }
        }
    }
}
