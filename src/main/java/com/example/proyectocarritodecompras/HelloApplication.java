package com.example.proyectocarritodecompras;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        store storetemp = store.getInstance();

        storetemp.loadAllClients("clients.txt");
        storetemp.loadAllSellers("sellers.txt");
        storetemp.loadAllProducts("products");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("bienvenida.fxml"));
        Parent root = loader.load();



        HelloController controller  = loader.getController();
        controller.setStore(storetemp);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}