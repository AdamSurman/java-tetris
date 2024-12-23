package javatetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        try {
          final var loader = new FXMLLoader(Objects.requireNonNull(
              getClass().getResource("/javatetris/game.fxml")));
          final var scene = new Scene(loader.load());

          stage.setScene(scene);
          stage.setTitle("Java Tetris");
          stage.resizableProperty().set(false);
          stage.setOnCloseRequest(_ -> System.exit(0));
          stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}