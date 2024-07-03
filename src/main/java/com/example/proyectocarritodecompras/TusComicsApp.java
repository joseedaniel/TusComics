package com.example.proyectocarritodecompras;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TusComicsApp extends Application {
    public static void llenarCombo(ComboBox<String> llenarCombo, ObservableList<String> categorias) {
        llenarCombo.setItems(categorias);
    }

    public static void main(String[] args) {
        launch();
    }

    private ListView<VBox> productInfo;

    @Override
    public void start(Stage stage) throws IOException {

        store storetemp = store.getInstance();

        storetemp.loadAllClients("clients.txt");
        storetemp.loadAllSellers("sellers.txt");
        storetemp.loadAllProducts("products.txt");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("bienvenida.fxml"));
        Parent root = loader.load();

        //Inyeccion.
        AppController controller = loader.getController();
        controller.setStore(storetemp);
        Scene scene = new Scene(root);

        // Buscar la ListView en la escena cargada
        productInfo = (ListView<VBox>) scene.lookup("#productInfo");
        if (productInfo != null) {
            for (nodeProduct product : storetemp.getAllProducts()) {
                productInfo.getItems().add(createProductVBox(product));
            }
        } else {
            System.err.println("ListView productInfo no encontrado.");
        }

        stage.setScene(scene);
        stage.show();
    }
    private VBox createProductVBox(nodeProduct product) {
        // Crear un VBox para cada producto basado en el FXML
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("producto.fxml"));
            VBox productBox = fxmlLoader.load();

            // Obtener el controlador del VBox y asignar valores
            ProductViewSellerController productController = fxmlLoader.getController();
            productController.setProduct(product);

            return productBox;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}