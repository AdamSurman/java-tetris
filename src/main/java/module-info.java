module com.example.javatetris {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires static lombok;


    opens javatetris to javafx.fxml;
    exports javatetris;
    exports javatetris.internal;
    opens javatetris.internal to javafx.fxml;
    exports javatetris.ui;
    opens javatetris.ui to javafx.fxml;
    exports javatetris.game;
    opens javatetris.game to javafx.fxml;
}