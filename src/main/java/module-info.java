module org.uoz.uwagaostryzakret {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens org.uoz.uwagaostryzakret to javafx.fxml;
    exports org.uoz.uwagaostryzakret;
    exports org.uoz.uwagaostryzakret.controllers;
    opens org.uoz.uwagaostryzakret.controllers to javafx.fxml;
    exports org.uoz.uwagaostryzakret.classes to com.fasterxml.jackson.databind;
}