package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainScene mainScene = new MainScene();
        primaryStage.setTitle("Graphics");
        primaryStage.setScene(mainScene.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
