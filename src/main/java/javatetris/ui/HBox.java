package javatetris.ui;

import javafx.geometry.Dimension2D;
import javatetris.internal.GameObject;

public class HBox extends ViewBox{
    public HBox(Dimension2D position) {
        super(position);
    }
    public HBox() {
        super();
    }

    @Override
    public void addChild(GameObject child) {
        child.setPosition(new Dimension2D(position.getWidth() + size.getWidth(), position.getHeight()));
        child.update();
        children.add(child);
        updateSize();
    }

    @Override
    public void updatePosition() {
        double xOffset = 0.0;
        for (var child : children) {
            child.setPosition(new Dimension2D(position.getWidth() + xOffset, position.getHeight()));
            xOffset += child.getSize().getWidth();
        }
    }

    @Override
    public void updateSize() {
        double sumWidth = children
                .stream()
                .map(GameObject::getSize)
                .map(Dimension2D::getWidth)
                .reduce(0.0, Double::sum);

        double maxHeight = children
                .stream()
                .map(GameObject::getSize)
                .map(Dimension2D::getHeight)
                .max(Double::compare)
                .orElse(0.0);

        size = new Dimension2D(sumWidth, maxHeight);
    }
}
