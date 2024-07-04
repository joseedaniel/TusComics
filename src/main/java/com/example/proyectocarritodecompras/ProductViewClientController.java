package com.example.proyectocarritodecompras;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductViewClientController {

    @FXML
    private Label idHistorieta;
    @FXML
    private Label nombreHistorieta;
    @FXML
    private ImageView añadirImagen;
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
    private String descripcion;
    private String categoia;

    public void setProduct(nodeProduct product) {
        idHistorieta.setText(product.idProduct);
        nombreHistorieta.setText(product.name);
        añadirImagen.setImage(new Image(product.urlImage));
        precioLabel.setText(String.format("$%.2f", product.price));
        cantidad.setText("1");
        descripcion = product.desc;
        categoia = product.category;
    }

    public void añadirACarrito() {
        nodeProduct producto = new nodeProduct(
                añadirImagen.getImage().getUrl(),
                idHistorieta.getText(),
                nombreHistorieta.getText(),
                descripcion,
                categoia,
                Integer.parseInt(cantidad.getText()),
                Double.parseDouble(precioLabel.getText())
        );
        System.out.println("ID: " + idHistorieta.getText());
    }
}
