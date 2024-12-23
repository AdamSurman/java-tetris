package javatetris.internal;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

public class GameController {
    @FXML
    private Canvas canvas;

    @FXML
    public Button btnRtLeft;

    @FXML
    public Button btnLeft;

    @FXML
    public Button btnRtRight;

    @FXML
    public Button btnRight;

    @FXML
    public Button btnDrop;

    @FXML
    public Button btnSwap;

    GameThread thread;

    @FXML
    void initialize() {
        thread = new GameThread(canvas, this);
        thread.start();
    }

    public void rtLeft(ActionEvent event) {
        thread.getScene().getEngine().tetrominoTryRotateLeft();
    }

    public void left(ActionEvent event) {
        thread.getScene().getEngine().tetrominoTryMoveLeft();
    }

    public void rtRight(ActionEvent event) {
        thread.getScene().getEngine().tetrominoTryRotateRight();
    }

    public void right(ActionEvent event) {
        thread.getScene().getEngine().tetrominoTryMoveRight();

    }

    public void drop(ActionEvent event) {
        thread.getScene().getEngine().tetrominoTryMoveDown();
    }

    public void swap(ActionEvent event) {}
}
