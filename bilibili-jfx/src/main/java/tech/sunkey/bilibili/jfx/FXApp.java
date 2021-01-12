package tech.sunkey.bilibili.jfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tech.sunkey.bilibili.jfx.view.MainController;

/**
 * @author Sunkey
 * @since 2021-01-12 9:00 上午
 **/
public class FXApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(MainController.class.getResource("MainView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("FXAPP");
        stage.setScene(scene);
        stage.show();
    }

}
