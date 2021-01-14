package tech.sunkey.bilibili.jfx.tests;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class Main extends Application {
	private Thread thread;
	private boolean isRunning = true;
	private PerspectiveCamera camera;
	private int speed = -1;
	private int count = 1;
	private int maxCount = 50;
	public Parent createContent() throws Exception {
        // Box
        Box testBox = new Box(5, 5, 5);
        testBox.setMaterial(new PhongMaterial(Color.BLUE));
        testBox.setDrawMode(DrawMode.FILL);
        
        // Create and position camera
        camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                new Rotate(-20, Rotate.Y_AXIS),
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -20));
 
        // Build the Scene Graph
        Group root = new Group();       
        root.getChildren().add(camera);
        root.getChildren().add(testBox);
 
        // Use a SubScene       
        SubScene subScene = new SubScene(root, 310,310, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.ALICEBLUE);
        
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);
        return group;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        Scene scene = new Scene(createContent(), 300, 300);
        thread = new Thread(new Runnable() {
			@Override
			public void run() {
			  	while(isRunning){
			  		try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			  		Platform.runLater(new Runnable() {
						@Override
						public void run() {
					        camera.getTransforms().addAll(
					                new Translate(0, 0,speed));
					        count++;
					        if(count >= maxCount){
					        	speed = -speed;
					        	count = 0;
					        }
						}
					});
			  	}
			}
		});
        thread.start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}