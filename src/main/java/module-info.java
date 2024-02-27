module org.example.yumlink_v3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens org.example.yumlink_v3 to javafx.fxml;
    exports org.example.yumlink_v3;
    exports Controllers;
    opens Controllers to javafx.fxml;
    opens Entities to javafx.base;
}