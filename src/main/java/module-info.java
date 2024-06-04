module com.example.proyectocarritodecompras {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyectocarritodecompras to javafx.fxml;
    exports com.example.proyectocarritodecompras;
}