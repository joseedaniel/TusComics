package com.example.proyectocarritodecompras;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class HelloController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Stack<String> sceneStack = new Stack<>();
    @FXML
    private TextField idUsuario;
    @FXML
    private PasswordField contraseña;
    @FXML
    private Button bRegresar;
    @FXML
    private Button registrar;
    @FXML
    private RadioButton tipoComprador;
    @FXML
    private RadioButton tipoVendedor;
    @FXML
    private store storetemp = store.getInstance();

//    public  HelloController() {
//        System.out.println("hola");
//        storetemp.getInstance().loadAllClients("clients.txt");
//        try {
//            storetemp.getInstance().loadAllSellers("sellers.txt");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setStore(store storetemp) {
        this.storetemp = storetemp;
    }


    @FXML
    private void iniciarSesion(ActionEvent event) {
        String idLog = idUsuario.getText();
        String password = contraseña.getText();

        nodeUser user = storetemp.getBuscarIdUser(idLog);
        if (user == null) {
            mostrarError("Error de inicio de sesión", "Usuario no encontrado", "El ID de usuario no existe.");
            return;
        }

        if (!user.getPassword().equals(password)) {
            mostrarError("Error de inicio de sesión", "Contraseña incorrecta", "La contraseña ingresada es incorrecta.");
            return;
        }

        storetemp.login(idLog, password);

        try {
            if (user instanceof nodeClient) {
                cambiarPantalla(event, "principalclientes.fxml");
            } else if (user instanceof nodeSeller) {
                cambiarPantalla(event, "principalvendedor.fxml");
            }
        } catch (IOException e) {
            mostrarError("Error", "No se pudo cargar la pantalla", "Ocurrió un error al intentar cargar la pantalla.");
            e.printStackTrace();
        }
    }


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


    public void irAIniciarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("iniciodesesion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) registrar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            mostrarError("Error", "No se pudo cargar la pantalla", "Ocurrió un error al intentar cargar la pantalla de inicio de sesión.");
            e.printStackTrace();
        }
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

    @FXML
    private void bRegresarUsos(ActionEvent event) throws IOException {
        if (!sceneStack.isEmpty()) {
            String previousScene = sceneStack.pop();
            changeScene(previousScene);
        }
        if (sceneStack.isEmpty()) {
            bRegresar.setVisible(false);
        }
    }

    public void changeScene(String fxmlFile) throws IOException {
        if (stage.getScene() != null && !stage.getScene().getRoot().getId().equals("principalclientes.fxml") || !stage.getScene().getRoot().getId().equals("principalclientes.fxml")) {
            sceneStack.push(stage.getScene().getRoot().getId());
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        root.setId(fxmlFile);
        HelloController controller = loader.getController();
        controller.setStage(stage);
        controller.sceneStack = this.sceneStack;
        controller.bRegresar.setVisible(!controller.sceneStack.isEmpty());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        bRegresar.setVisible(false);

    }

    @FXML
    private void handleLogoutButtonAction() throws IOException {
        storetemp.getInstance().logout();
        sceneStack.clear();
        changeScene("bienvenida.fxml");
    }

    private void mostrarError(String titulo, String cabecera, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecera);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void mostrarAlerta(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    private void cambiarPantalla(ActionEvent event, String fxmlPath) throws IOException {
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new javafx.scene.Scene(javafx.fxml.FXMLLoader.load(getClass().getResource(fxmlPath))));
        stage.show();
    }

    @FXML
    private void registrarUsuario() {
        String idLog = idUsuario.getText();
        String password = contraseña.getText();

        try {
            if (storetemp.isUserExists(idLog)) {
                throw new IllegalArgumentException("Ya existe un usuario con ese ID.");
            }
            boolean esComprador = tipoComprador.isSelected(); // Determinar si es comprador o vendedor
            storetemp.registrarUsuario(idLog, password, esComprador);

            if (esComprador) {
                storetemp.saveAllClients("clients.txt");
            } else {
                storetemp.saveAllSellers("sellers.txt");
            }

            mostrarAlerta("Registro exitoso", "Usuario registrado", "Se ha registrado correctamente como " + (esComprador ? "Cliente" : "Vendedor") + ".");
            irAIniciarSesion();
        } catch (IllegalArgumentException e) {
            mostrarError("Error", "Registro fallido", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

