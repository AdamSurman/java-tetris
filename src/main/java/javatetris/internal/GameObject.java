package javatetris.internal;

import javafx.geometry.Dimension2D;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public abstract class GameObject {
    @Getter @Setter
    protected Dimension2D position;
    @Getter @Setter
    protected Dimension2D size;
    protected ArrayList<GameObject> children;

    protected GameObject() {}
    protected GameObject(Dimension2D position, Dimension2D size) {
        this.position = position;
        this.size = size;
        children = new ArrayList<>();
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
}
