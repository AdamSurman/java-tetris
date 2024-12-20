package javatetris.ui;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javatetris.internal.Drawable;
import javatetris.internal.GameObject;
import javatetris.internal.Simulatable;

public abstract class ViewBox extends GameObject implements Drawable, Simulatable {
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

    @Override
    public void simulate(float deltaTime) {
        for (var child : children) {
            if (child instanceof Simulatable simulatable) {
                simulatable.simulate(deltaTime);
            }
        }
    }
}
