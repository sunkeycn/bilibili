package tech.sunkey.bilibili.jfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Sunkey
 * @since 2021-01-12 9:00 上午
 **/
public class FXApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400,400);
        stage.setScene(scene);
        stage.show();
    }

}
