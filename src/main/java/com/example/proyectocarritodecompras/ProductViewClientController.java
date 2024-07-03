package com.example.proyectocarritodecompras;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ProductViewClientController {
        @FXML
        private VBox productoSpace;
        @FXML
        private Label idHistorieta;
        @FXML
        private Label nombreHistorieta;
        @FXML
        private ImageView imagenHistorieta;
        @FXML
        private Label precioLabel;
        @FXML
        private Button bDecrementar;
        @FXML
        private TextField cantidad;
        @FXML
        private Button bIncrementar;
        @FXML
        private Button bAgregarCarrito;

        public void setProduct(nodeProduct product) {
            idHistorieta.setText(product.idProduct);
            nombreHistorieta.setText(product.name);
            imagenHistorieta.setImage(new Image(product.urlImage));
            precioLabel.setText(String.format("$%.2f", product.price));
            cantidad.setText("0");

        }
    }
