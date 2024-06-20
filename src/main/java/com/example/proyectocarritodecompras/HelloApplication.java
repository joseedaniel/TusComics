package com.example.proyectocarritodecompras;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void llenarCombo(ComboBox<String> llenarCombo, ObservableList<String> categorias) {
        llenarCombo.setItems(categorias);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        store storetemp = store.getInstance();

        storetemp.loadAllClients("clients.txt");
        storetemp.loadAllSellers("sellers.txt");
        storetemp.loadAllProducts("products.txt");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("bienvenida.fxml"));
        Parent root = loader.load();

        //Inyeccion.
        HelloController controller = loader.getController();
        controller.setStore(storetemp);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}