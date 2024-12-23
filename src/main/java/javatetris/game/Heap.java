package javatetris.game;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javatetris.internal.Drawable;
import javatetris.internal.GameObject;

import java.util.*;

public class Heap extends GameObject implements Drawable {
    Heap(GameObject parentArea) {
        position = parentArea.getPosition();
        size = parentArea.getSize();
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (var child : children) {
            if (child instanceof Drawable drawable) {
                drawable.draw(gc);
            }
        }
    }

    public Collection<Double> checkFullRows() {
        ArrayList<Double> fullRows = new ArrayList<>();
        HashMap<Double, Integer> rowCount = new HashMap<>();
        for (var child : children)
            rowCount.merge(child.getPosition().getHeight(), 1, Integer::sum);
        for (var row : rowCount.entrySet())
            if (row.getValue() == 10)
                fullRows.add(row.getKey());
        Collections.sort(fullRows);
        return fullRows;
    }

    public void removeRow(double y) {
        children.removeIf(child -> child.getPosition().getHeight() == y);
        for (var child : children)
            if (child.getPosition().getHeight() < y)
              child.setPosition(
                  new Dimension2D(child.getPosition().getWidth(),
                                  child.getPosition().getHeight() + 32));
    }

    public boolean isFull() {
        for (var child : children)
          if (child.getPosition().getHeight() <=
              position.getHeight() +
                  32) // I HAVE NO IDEA WHY I NEED TO ADD 32 BUT IT DOESN'T WORK
                      // WITHOUT IT FOR SOME REASON.
            return true;
        return false;
    }
}
