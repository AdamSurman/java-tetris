package javatetris.ui;

import javafx.geometry.Dimension2D;
import javatetris.internal.GameObject;

public class VBox extends ViewBox{
    public VBox(Dimension2D position) {
        super(position);
    }
    public VBox() {
        super();
    }

    @Override
    public void addChild(GameObject child) {
        child.setPosition(new Dimension2D(position.getWidth(), position.getHeight() + size.getHeight()));
        child.update();
        children.add(child);
        updateSize();
    }

    @Override
    public void updatePosition() {
        double yOffset = 0.0;
        for (var child : children) {
            child.setPosition(new Dimension2D(position.getWidth(), position.getHeight() + yOffset));
            yOffset += child.getSize().getHeight();
        }
    }

    @Override
    public void updateSize() {
        double maxWidth = children
                .stream()
                .map(GameObject::getSize)
                .map(Dimension2D::getWidth)
                .max(Double::compare)
                .orElse(0.0);

        double sumHeight = children
                .stream()
                .map(GameObject::getSize)
                .map(Dimension2D::getHeight)
                .reduce(0.0, Double::sum);

        size = new Dimension2D(maxWidth, sumHeight);
    }
}
