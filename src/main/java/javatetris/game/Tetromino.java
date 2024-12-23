package javatetris.game;

import javafx.geometry.Dimension2D;
import javafx.scene.canvas.GraphicsContext;
import javatetris.internal.Drawable;
import javatetris.internal.GameObject;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class Tetromino extends GameObject implements Drawable{
    public enum Shape {
        I,
        J,
        L,
        O,
        S,
        T,
        Z;

        /* These 2 need to be static for the RNG to work. */
        static final Random rng = new Random();
        public static Shape getRandomShape() {
            return Shape.class.getEnumConstants()[rng.nextInt(Shape.class.getEnumConstants().length)];
        }

        public Block.Color getColor() {
            return switch (this) {
                case I -> Block.Color.CYAN;
                case J -> Block.Color.BLUE;
                case L -> Block.Color.ORANGE;
                case O -> Block.Color.YELLOW;
                case S -> Block.Color.GREEN;
                case T -> Block.Color.MAGENTA;
                case Z -> Block.Color.RED;
            };
        }

        public Dimension2D[] getBlockPositions() {
            return switch (this) {
                case I -> new Dimension2D[] {
                        new Dimension2D(0, 0),
                        new Dimension2D(0, 1),
                        new Dimension2D(0, 2),
                        new Dimension2D(0, 3)
                };
                case J -> new Dimension2D[] {
                        new Dimension2D(0, 0),
                        new Dimension2D(0, 1),
                        new Dimension2D(0, 2),
                        new Dimension2D(1, 0)
                };
                case L -> new Dimension2D[] {
                        new Dimension2D(0, 0),
                        new Dimension2D(1, 0),
                        new Dimension2D(1, 1),
                        new Dimension2D(1, 2)
                };
                case O -> new Dimension2D[] {
                        new Dimension2D(0, 0),
                        new Dimension2D(0, 1),
                        new Dimension2D(1, 0),
                        new Dimension2D(1, 1)
                };
                case S -> new Dimension2D[] {
                        new Dimension2D(0, 0),
                        new Dimension2D(0, 1),
                        new Dimension2D(1, 1),
                        new Dimension2D(1, 2)
                };
                case T -> new Dimension2D[] {
                        new Dimension2D(1, 0),
                        new Dimension2D(0, 1),
                        new Dimension2D(1, 1),
                        new Dimension2D(2, 1)
                };
                case Z -> new Dimension2D[] {
                        new Dimension2D(0, 1),
                        new Dimension2D(0, 2),
                        new Dimension2D(1, 0),
                        new Dimension2D(1, 1)
                };
            };
        }

    }
    final Shape shape;

    public Tetromino(Shape shape) {
        this.shape = shape;
        for (var offset : shape.getBlockPositions()) {
                    addChild(new Block(
                        new Dimension2D(
                            position.getWidth() + 32 * offset.getWidth(),
                            position.getHeight() + 32 * offset.getHeight()),
                        shape.getColor()));
        }
    }

    @Override
    public void setPosition(Dimension2D position) {
        var oldPosition = this.position;
        super.setPosition(position);
        for (var child : children) {
                    child.setPosition(new Dimension2D(
                        child.getPosition().getWidth() + position.getWidth() -
                            oldPosition.getWidth(),
                        child.getPosition().getHeight() + position.getHeight() -
                            oldPosition.getHeight()));
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (var child : children) {
            if (child instanceof Drawable drawable) {
                drawable.draw(gc);
            }
        }
    }

    public double getWidth() {
        return children.stream()
                .map(GameObject::getPosition)
                .map(Dimension2D::getWidth)
                .max(Double::compare)
                .orElse(0.0) + 32;
    }
    public double getHeight() {
        return children.stream()
                .map(GameObject::getPosition)
                .map(Dimension2D::getHeight)
                .max(Double::compare)
                .orElse(0.0) + 32;
    }

    public void moveLeft() {
        setPosition(new Dimension2D(position.getWidth() - 32, position.getHeight()));
    }

    public void moveRight() {
        setPosition(new Dimension2D(position.getWidth() + 32, position.getHeight()));
    }

    public void moveDown() {
        setPosition(new Dimension2D(position.getWidth(), position.getHeight() + 32));
    }

    public void moveUp() {
        setPosition(new Dimension2D(position.getWidth(), position.getHeight() - 32));
    }

    public void rotateRight() {
        for (var child : children)
          child.setPosition(new Dimension2D(
              child.getPosition().getWidth() - position.getWidth(),
              child.getPosition().getHeight() - position.getHeight()));

        for (var child : children)
            child.setPosition(new Dimension2D(0 - child.getPosition().getHeight(), child.getPosition().getWidth()));

        var offset = 0 - children.stream().map(GameObject::getPosition).map(Dimension2D::getWidth).min(Double::compare).orElse(0.0);

        for (var child : children) {
            child.setPosition(new Dimension2D(child.getPosition().getWidth() + offset, child.getPosition().getHeight()));
        }

        for (var child : children)
          child.setPosition(new Dimension2D(
              child.getPosition().getWidth() + position.getWidth(),
              child.getPosition().getHeight() + position.getHeight()));
    }
    public void rotateLeft() {
        for (var child : children)
            child.setPosition(new Dimension2D(
                    child.getPosition().getWidth() - position.getWidth(),
                    child.getPosition().getHeight() - position.getHeight()));

        for (var child : children)
            child.setPosition(new Dimension2D(child.getPosition().getHeight(), 0 - child.getPosition().getWidth()));

        var height = 0 - children.stream().map(GameObject::getPosition).map(Dimension2D::getHeight).min(Double::compare).orElse(0.0);

        for (var child : children) {
            child.setPosition(new Dimension2D(child.getPosition().getWidth(), child.getPosition().getHeight() + height));
        }

        for (var child : children)
            child.setPosition(new Dimension2D(
                    child.getPosition().getWidth() + position.getWidth(),
                    child.getPosition().getHeight() + position.getHeight()));
    }

    Tetromino(Tetromino template) {
        position = new Dimension2D(template.position.getWidth(), template.position.getHeight());
        size = new Dimension2D(template.size.getWidth(), template.size.getHeight());
        children = new ArrayList<>();
        for (var child : template.children) {
            if (child instanceof Block block)
                children.add(block.copy());
        }
        shape = template.shape;
    }

    public Tetromino copy() {
        return new Tetromino(this);
    }
}
