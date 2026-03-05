module com.example.module3assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.scripting;


    opens com.example.module3assignment to javafx.fxml;
    exports com.example.module3assignment;
}