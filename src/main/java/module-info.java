module org.example.acividad6t4diseno {
    requires javafx.fxml;
    requires net.synedra.validatorfx;
    requires javafx.controls;


    opens org.example.acividad6t4diseno to javafx.fxml;
    exports org.example.acividad6t4diseno;
}