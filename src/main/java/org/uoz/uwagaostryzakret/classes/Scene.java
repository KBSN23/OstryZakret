package org.uoz.uwagaostryzakret.classes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.uoz.uwagaostryzakret.Application;

import java.io.IOException;

abstract public class Scene  {
    private javafx.scene.Scene scene;
    private String title;
    private String resourceName;
    private final int width = 800;
    private final int height = 600;
    protected Stage stage;
    protected FXMLLoader loader;

    public Scene(Stage stage, String title, String resourceName) throws IOException {
        this.stage = stage;
        this.title = title;
        this.resourceName = resourceName;
        this.loader = new FXMLLoader(Application.class.getResource(this.resourceName));
        this.scene = new javafx.scene.Scene(loader.load(), width, height);

        this.stage.setTitle(title);
    }

    abstract public void setupController();

    public void setScene(){
        this.stage.setScene(this.scene);
    }

    public void show(){
        this.stage.show();
    }
}
