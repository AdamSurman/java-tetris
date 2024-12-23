package javatetris.internal;

import javafx.geometry.Dimension2D;
import lombok.Getter;
import lombok.Setter;

import javafx.geometry.Rectangle2D;
import java.util.ArrayList;

public abstract class GameObject {
    @Getter @Setter
    protected Dimension2D position = new Dimension2D(0, 0);
    @Getter @Setter
    protected Dimension2D size = new Dimension2D(0, 0);
    @Getter
    protected ArrayList<GameObject> children = new ArrayList<>();

    protected GameObject() {}
    protected GameObject(Dimension2D position, Dimension2D size) {
        this.position = position;
        this.size = size;
    }

    public void update() {
        updatePosition();
        for (var child : children) {
            child.update();
        }
    }
    public void updateSize() {
    }
    public void updatePosition() {
    }

    public void addChild(GameObject child) {
        children.add(child);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getWidth(), position.getHeight(), size.getWidth(), size.getHeight());
    }
}
