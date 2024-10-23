module org.uoz.uwagaostryzakret {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.uoz.uwagaostryzakret to javafx.fxml;
    exports org.uoz.uwagaostryzakret;
    exports org.uoz.uwagaostryzakret.scenes;
    opens org.uoz.uwagaostryzakret.scenes to javafx.fxml;
}