package org.uoz.uwagaostryzakret.classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.uoz.uwagaostryzakret.Application;

import java.io.IOException;

public class Scene  {
    private javafx.scene.Scene scene;
    private String resourceName;
    private final int width = 1600;
    private final int height = 900;
    public Stage stage;
    public FXMLLoader loader;

    public Scene(Stage stage, String title, String resourceName, History history, Options options) throws IOException {
        history.add(this);

        this.stage = stage;
        this.resourceName = resourceName;
        this.loader = new FXMLLoader(Application.class.getResource(this.resourceName));
        this.scene = new javafx.scene.Scene(loader.load(), width, height);
        this.stage.setTitle(title);

        Controller controller = loader.getController();
        controller.setup(stage, history, options);
    }

    public Scene(Stage stage, String title, String resourceName) throws IOException {
        this.stage = stage;
        this.resourceName = resourceName;
        this.loader = new FXMLLoader(Application.class.getResource(this.resourceName));
        this.scene = new javafx.scene.Scene(loader.load(), width, height);
        this.stage.setTitle(title);

        Controller controller = loader.getController();
        controller.setup(stage);
    }

    public void setScene(){
        this.stage.setScene(this.scene);
    }

    public void show(){
        this.stage.show();

        Controller controller = loader.getController();
        controller.init();
    }
}
