package tech.sunkey.bilibili.jfx.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Sunkey
 * @since 2021-01-12 21:57
 **/
public class MainController {

    @FXML
    private Button myBtn;

    public void clickBtn(ActionEvent event) {
        System.out.println("Clicked");
    }

}
