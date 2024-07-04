package com.example.proyectocarritodecompras;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;

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
    private String categoria;


    public void setProduct(nodeProduct product) {

        idHistorieta.setText(product.idProduct);
        nombreHistorieta.setText(product.name);
        añadirImagen.setImage(new Image(product.urlImage));
        precioLabel.setText(String.format("$%.2f", product.price));
        cantidad.setText("1");
        descripcion = product.desc;
        categoria = product.category;
    }

    @FXML
    public void añadirACarrito() throws IOException {
        String session = readSession();
        String producto =
                añadirImagen.getImage().getUrl() + "," +
                        idHistorieta.getText() + "," +
                        nombreHistorieta.getText() + "," +
                        descripcion + "," +
                        categoria + "," +
                        Integer.parseInt(cantidad.getText()) + "," +
                        Double.parseDouble(precioLabel.getText().replace("$", "")
                        )+","+session;
        BufferedWriter writer = new BufferedWriter(new FileWriter("shoppingcart.txt"));
        writer.write(producto);
        writer.newLine();
        writer.close();
        System.out.println("Producto agregado al carrito. ID: " + idHistorieta.getText());
    }

    private String readSession() {
        String filePath = "currentsession.txt"; // Ruta al archivo que quieres leer

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String primeraLinea = reader.readLine(); // Lee la primera línea
            return primeraLinea;
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return "";
        }
    }


//    public void añadirACarrito() {
//        nodeProduct producto = new nodeProduct(
//                añadirImagen.getImage().getUrl(),
//                idHistorieta.getText(),
//                nombreHistorieta.getText(),
//                descripcion,
//                categoria,
//                Integer.parseInt(cantidad.getText()),
//                Double.parseDouble(precioLabel.getText())
//        );
//        System.out.println("ID: " + idHistorieta.getText());
//    }
}
