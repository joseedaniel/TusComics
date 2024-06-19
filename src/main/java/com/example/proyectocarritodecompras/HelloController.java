package com.example.proyectocarritodecompras;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField usuario;
    @FXML
    private TextField contraseña;
    // private final store store = new store();
    private final List<nodeClient> compradores = new ArrayList<>();
    private final List<nodeSeller> vendedores = new ArrayList<>();
    @FXML
    private Button bIniciarSesion;


    public void irABienvenida(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("bienvenida.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irARegistro(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("registro.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irAIniciarSesion(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("iniciodesesion.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irAPrincipalCliente(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("principalclientes.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irAPrincipalVendedor(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("principalvendedor.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irACarrito(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("carritodecompras.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irAListaDeDeseos(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("listadedeseos.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irAHistorialCliente(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("historialcliente.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irAHistorialVendedor(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("historialvendedor.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void vendedorOComprador(ActionEvent event) throws IOException {
        String username = usuario.getText();
        String password = contraseña.getText();

        try {
            if (esComprador(username, password)) {
                loginCompradorOVendedor("principalclientes.fxml");
            } else if (esVendedor(username, password)) {
                loginCompradorOVendedor("principalvendedor.fxml");
            } else {
                System.out.println("Credenciales incorrectas");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean esComprador(String username, String password) {
        return compradores.stream().anyMatch(u -> u.idLog.equals(username) && u.pass.equals(password));
    }

    private boolean esVendedor(String username, String password) {
        return vendedores.stream().anyMatch(u -> u.idLog.equals(username) && u.pass.equals(password));
    }

    public void loginCompradorOVendedor(String fxmlFile) throws IOException {
        Stage stage = (Stage) bIniciarSesion.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}