module com.example.beta {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires javax.servlet.api;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires android.json;
    requires com.google.gson;
    requires freetts;

    requires org.apache.pdfbox;
    opens com.example.beta to javafx.fxml;
    //opens com.example.beta.Models to javafx.fxml;
    exports com.example.beta;
    opens com.example.beta.Models to javafx.base;
    exports com.example.beta.Controller;
    opens com.example.beta.Controller to javafx.fxml;
}