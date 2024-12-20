package javatetris.internal;

import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
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
        thread = new GameThread(canvas);
        thread.start();
    }

    public void rtLeft(ActionEvent event) {}

    public void left(ActionEvent event) {
        thread.getScene().getObjects().getFirst().setPosition(new Dimension2D(thread.getScene().getObjects().getFirst().getPosition().getWidth() - 1, thread.getScene().getObjects().getFirst().getPosition().getHeight()));
    }

    public void rtRight(ActionEvent event) {}

    public void right(ActionEvent event) {
        thread.getScene().getObjects().getFirst().setPosition(new Dimension2D(thread.getScene().getObjects().getFirst().getPosition().getWidth() + 1, thread.getScene().getObjects().getFirst().getPosition().getHeight()));

    }

    public void drop(ActionEvent event) {}

    public void swap(ActionEvent event) {}
}
