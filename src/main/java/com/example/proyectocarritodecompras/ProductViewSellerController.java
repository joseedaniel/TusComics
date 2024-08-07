package com.example.proyectocarritodecompras;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductViewSellerController {

    @FXML
    private Label idHistorieta;
    @FXML
    private Label nombreHistorieta;
    @FXML
    private ImageView añadirImagen;
    @FXML
    private Label precioLabel;

    public void setProduct(nodeProduct product) {
        idHistorieta.setText(product.idProduct);
        nombreHistorieta.setText(product.name);
        añadirImagen.setImage(new Image(product.urlImage));
        precioLabel.setText(String.format("$%.2f", product.price));

    }
}
