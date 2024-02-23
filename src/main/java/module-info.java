module com.example.beta {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires javax.servlet.api;

    opens com.example.beta to javafx.fxml;
    exports com.example.beta;
    exports com.example.beta.Controller;
    opens com.example.beta.Controller to javafx.fxml;
}