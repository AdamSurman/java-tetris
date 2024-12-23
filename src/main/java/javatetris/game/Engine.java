package javatetris.game;

import javafx.geometry.Dimension2D;
import javatetris.Scene;

import static java.lang.Math.floor;

public class Engine {
    Scene parentScene;
    Tetromino tetromino;
    Tetromino nextTetromino;
    Heap heap;

    public Engine(Scene parentScene) {
        this.parentScene = parentScene;
        nextTetromino = new Tetromino(Tetromino.Shape.getRandomShape());
        heap = new Heap(parentScene.getGameArea());
        parentScene.getGameArea().addChild(heap);
        addTetromino();
    }

    public void addTetromino() {
        tetromino = nextTetromino;
        tetromino.setPosition(new Dimension2D(
                parentScene.getGameArea().getPosition().getWidth() + 128,
                parentScene.getGameArea().getPosition().getHeight()));

        parentScene.getGameArea().addChild(tetromino);

        nextTetromino = new Tetromino(Tetromino.Shape.getRandomShape());
    }

    void putTetrominoToHeap() {
        for (var block : tetromino.getChildren()) {
            heap.addChild(((Block)block).copy());
        }
        parentScene.getGameArea().getChildren().remove(tetromino);
        addTetromino();
    }

    public void tetrominoTryMoveDown() {
        var offsetX = floor(tetromino.getPosition().getWidth() - parentScene.getGameArea().getPosition().getWidth()) % 32;
        var offsetY = floor(tetromino.getPosition().getHeight() - parentScene.getGameArea().getPosition().getHeight()) % 32;

        tetromino.setPosition(
                new Dimension2D(tetromino.getPosition().getWidth() - offsetX,
                        tetromino.getPosition().getHeight() - offsetY));

        var tetrominoCopy = tetromino.copy();
        tetrominoCopy.moveDown();
        boolean movable = true;
        top:
        for (var block : tetrominoCopy.getChildren()) {
            for (var heapBlock : heap.getChildren())
                if (block.getPosition().equals(heapBlock.getPosition())) {
                    movable = false;
                    break top;
                }
            if (block.getPosition().getHeight() >=
                    parentScene.getGameArea().getPosition().getHeight() +
                            parentScene.getGameArea().getSize().getHeight()) {
                movable = false;
                break;
            }
        }
        if (movable)
            tetromino.moveDown();
        else
            putTetrominoToHeap();
    }

    public void tetrominoTryMoveLeft() {
        var tetrominoCopy = tetromino.copy();
        tetrominoCopy.moveLeft();
        boolean movable = true;
        top:
        for (var block : tetrominoCopy.getChildren()) {
            for (var heapBlock : heap.getChildren())
                if (block.getPosition().equals(heapBlock.getPosition())) {
                    movable = false;
                    break top;
                }
            if (block.getPosition().getWidth() <
                    parentScene.getGameArea().getPosition().getWidth()) {
                movable = false;
                break;
            }
        }
        if (movable)
            tetromino.moveLeft();
    }

    public void tetrominoTryMoveRight() {
        var tetrominoCopy = tetromino.copy();
        tetrominoCopy.moveRight();
        boolean movable = true;
        top:
        for (var block : tetrominoCopy.getChildren()) {
            for (var heapBlock : heap.getChildren())
                if (block.getPosition().equals(heapBlock.getPosition())) {
                    movable = false;
                    break top;
                }
            if (block.getPosition().getWidth() >=
                    parentScene.getGameArea().getPosition().getWidth() +
                            parentScene.getGameArea().getSize().getWidth()) {
                movable = false;
                break;
            }
        }
        if (movable)
            tetromino.moveRight();
    }

    enum Validity {
        YES,
        TOO_HIGH,
        TOO_WIDE
    }

    public void tetrominoTryRotateLeft() {
        var tetrominoCopy = tetromino.copy();
        tetrominoCopy.rotateLeft();
        if (checkForValidRotation(tetrominoCopy)) return;
        tetromino.rotateLeft();
        moveToValid();
    }

    public void tetrominoTryRotateRight() {
        var tetrominoCopy = tetromino.copy();
        tetrominoCopy.rotateRight();
        if (checkForValidRotation(tetrominoCopy)) return;
        tetromino.rotateRight();
        moveToValid();
    }

    private void moveToValid() {
        Validity valid = getValidity(tetromino);
        while (!valid.equals(Validity.YES)) {
            if (valid == Validity.TOO_HIGH) {
                tetromino.moveUp();
                valid = getValidity(tetromino);
            } else if (valid == Validity.TOO_WIDE) {
                tetromino.moveLeft();
                valid = getValidity(tetromino);
            }
        }
    }

    private boolean checkForValidRotation(Tetromino tetrominoCopy) {
        Validity valid = getValidity(tetrominoCopy);
        while (!valid.equals(Validity.YES)) {
            if (valid == Validity.TOO_HIGH) {
                tetrominoCopy.moveUp();
                boolean movableUp = true;
                top:
                for (var block : tetrominoCopy.getChildren()) {
                    for (var heapBlock : heap.getChildren())
                        if (block.getPosition().equals(heapBlock.getPosition())) {
                            movableUp = false;
                            break top;
                        }
                    if (block.getPosition().getHeight() <=
                            parentScene.getGameArea().getPosition().getHeight()) {
                        movableUp = false;
                        break;
                    }
                }
                if (!movableUp)
                    return true;
                valid = getValidity(tetrominoCopy);
            }
            else if (valid == Validity.TOO_WIDE) {
                tetrominoCopy.moveLeft();
                boolean movableLeft = true;
                top:
                for (var block : tetrominoCopy.getChildren()) {
                    for (var heapBlock : heap.getChildren())
                        if (block.getPosition().equals(heapBlock.getPosition())) {
                            movableLeft = false;
                            break top;
                        }
                    if (block.getPosition().getWidth() <=
                            parentScene.getGameArea().getPosition().getWidth()) {
                        movableLeft = false;
                        break;
                    }
                }
                if (!movableLeft)
                    return true;
                valid = getValidity(tetrominoCopy);
            }
        }
        return false;
    }

    private Validity getValidity(Tetromino tetrominoCopy) {
        Validity valid = Validity.YES;
        top:
        for (var block : tetrominoCopy.getChildren()) {
            for (var heapBlock : heap.getChildren())
                if (block.getPosition().equals(heapBlock.getPosition())) {
                    if (tetromino.getHeight() < tetrominoCopy.getHeight())
                        valid = Validity.TOO_HIGH;
                    else if (tetromino.getWidth() < tetrominoCopy.getWidth())
                        valid = Validity.TOO_WIDE;
                    break top;
                }
            if (block.getPosition().getWidth() >=
                    parentScene.getGameArea().getPosition().getWidth() +
                        parentScene.getGameArea().getSize().getWidth()) {
                valid = Validity.TOO_WIDE;
                break;
            }
            if (block.getPosition().getHeight() >=
                parentScene.getGameArea().getPosition().getHeight() +
                    parentScene.getGameArea().getSize().getHeight()) {
                valid = Validity.TOO_HIGH;
                break;
            }
        }
        return valid;
    }


    public boolean checkHeapRows() {
        for (var row : heap.checkFullRows()) {
            heap.removeRow(row);
            System.out.println("+ 100");
            parentScene.setScore(parentScene.getScore() + 100);
        }
        return heap.isFull();
    }
}
