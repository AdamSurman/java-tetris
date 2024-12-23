package javatetris.ui;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javatetris.internal.Drawable;
import javatetris.internal.GameObject;

public abstract class ViewBox extends GameObject implements Drawable {
    protected ViewBox(Dimension2D position) {
        super(position, new Dimension2D(0, 0));
    }
    protected ViewBox() {
        this(new Dimension2D(0, 0));
    }

    @Override
    public abstract void addChild(GameObject child);

    @Override
    public void draw(GraphicsContext gc) {
        for (var child : children) {
            if (child instanceof Drawable drawable) {
                drawable.draw(gc);
            }
        }
    }
}
