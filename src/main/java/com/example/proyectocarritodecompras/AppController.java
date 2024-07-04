package com.example.proyectocarritodecompras;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.example.proyectocarritodecompras.TusComicsApp.llenarCombo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    ObservableList<String> categorias = FXCollections.observableArrayList("Comedia", "Acción", "Terror");
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String selectedImagePath;
    private nodeClient currentUser;
    private ObservableList<nodeProduct> productos;

    @FXML
    private TextField idUsuario;
    @FXML
    private PasswordField contraseña;
    @FXML
    private Button registrar;
    @FXML
    private RadioButton tipoComprador;
    @FXML
    private store storetemp = store.getInstance();
    @FXML
    private ImageView añadirImagen;
    @FXML
    private TextField nombreProducto;
    @FXML
    private TextField descripcionProducto;
    @FXML
    private TextField cantidadProducto;
    @FXML
    private TextField precioProducto;
    @FXML
    private ComboBox<String> categoriaProducto;
    @FXML
    private TextField idProducto;
    @FXML
    private Label nombreHistorieta;
    @FXML
    private Label idHistorieta;
    @FXML
    private Label precioLabel;
    @FXML
    private ListView<nodeProduct> productInfo;
    @FXML
    private VBox mainVBox;
    @FXML
    private GridPane espacioProducto;


    public void setStore(store storetemp) {
        this.storetemp = storetemp;
    }

    @FXML
    public void cargarProductos() {
        if (productInfo != null) {
            ObservableList<nodeProduct> productos = FXCollections.observableArrayList(storetemp.getAllProducts());
            productInfo.setItems(productos);
            productInfo.setCellFactory(param -> new ListCell<nodeProduct>() {
                @Override
                protected void updateItem(nodeProduct item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.name == null) {
                        setText(null);
                    } else {
                        VBox vbox = createProductVBox(item);
                        mainVBox = vbox;
                        setGraphic(vbox);
                    }
                }
            });
        } else {
            System.err.println("ListView productInfo no encontrado.");
        }
    }

    private VBox createProductVBox(nodeProduct product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("productviewseller.fxml"));
            VBox productBox = loader.load();
            ProductViewSellerController productController = loader.getController();
            productController.setProduct(product);
            return productBox;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    private void cargarProductView() {
        if (mainVBox == null) {
            System.err.println("mainVBox no inicializado correctamente.");
            return;
        }

        mainVBox.getChildren().clear(); // Limpiar contenido previo

    }


    //Registro/Login
    @FXML
    private void registrarUsuario() {
        String idLog = idUsuario.getText();
        String password = contraseña.getText();

        try {
            if (storetemp.isUserExists(idLog)) {
                throw new IllegalArgumentException("Ya existe un usuario con ese ID.");
            }
            boolean esComprador = tipoComprador.isSelected();
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
                currentUser = (nodeClient) user;
                productos = FXCollections.observableArrayList(storetemp.getAllProducts());
                BufferedWriter writer = new BufferedWriter(new FileWriter("currentsession.txt", false));
                writer.write(user.idLog);
                writer.newLine();
                writer.close();
                cambiarPantalla(event, "principalclientes.fxml");
            } else if (user instanceof nodeSeller) {
                cambiarPantalla(event, "principalvendedor2.fxml");
            }

        } catch (IOException e) {
            mostrarError("Error", "No se pudo cargar la pantalla", "Ocurrió un error al intentar cargar la pantalla.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProduct(ActionEvent event) throws IOException {
        String id = idProducto.getText();
        String nombre = nombreProducto.getText();
        String descripcion = descripcionProducto.getText();
        String categoria = categoriaProducto.getValue();
        int cantidad;
        double precio;

        if (id.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || categoria == null ||
                cantidadProducto.getText().isEmpty() || precioProducto.getText().isEmpty()) {
            showErrorAlert("Campos vacíos", "Todos los campos deben estar llenos.");
            return;
        }

        try {
            cantidad = Integer.parseInt(cantidadProducto.getText());
            precio = Double.parseDouble(precioProducto.getText());
        } catch (NumberFormatException e) {
            showErrorAlert("Error de formato", "Cantidad y precio deben ser números.");
            return;
        }

        if (selectedImagePath == null) {
            showErrorAlert("Imagen no seleccionada", "Por favor selecciona una imagen para el producto.");
            return;
        }

        store storeInstance = store.getInstance();
        nodeProduct existingProduct = storeInstance.getProductById(id);

        if (existingProduct != null) {
            showErrorAlert("Producto existente", "Ya existe un producto con el mismo ID.");
            return;
        }

        nodeProduct product = new nodeProduct(selectedImagePath, id, nombre, descripcion, categoria, cantidad, precio);

        storeInstance.addProduct(product);
        cargarProductos();

        showInfoAlert("Producto agregado", "El producto se ha agregado exitosamente.");
        storeInstance.saveAllProducts("products.txt");

        //TRABAJANDO
        FXMLLoader loader = new FXMLLoader(getClass().getResource("principalvendedor2.fxml"));
        Parent root = loader.load();
        AppController controller = loader.getController();
        ObservableList<nodeProduct> productos = FXCollections.observableArrayList(storetemp.getAllProducts());
        int numColumns = 3;
        for (int i = 0; i < productos.size(); i++) {
            FXMLLoader loaderProductView = new FXMLLoader(getClass().getResource("productviewseller.fxml"));
            Node node = loaderProductView.load();
            ProductViewSellerController controllerProductView = loaderProductView.getController();
            controllerProductView.setProduct(productos.get(i));
            int row = i / numColumns;
            int col = i % numColumns;
            controller.espacioProducto.add(node, col, row + 3);
        }

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // Método para navegar a la vista del vendedor
    public void irAPrincipalVendedor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("principalvendedor2.fxml"));
        Parent root = loader.load();
        AppController controller = loader.getController();
        controller.setStore(storetemp);
        Scene scene = new Scene(root);
        Stage stage = (Stage) (event != null ? ((Node) event.getSource()).getScene().getWindow() : registrar.getScene().getWindow());
        stage.setScene(scene);
        stage.show();
    }


    //Navegacion
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

    public void irAComedia(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("comedia.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irATerror(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("terror.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irAAccion(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("accion.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irAPrincipalCliente(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("principalclientes.fxml"));
        ObservableList<nodeProduct> productos = FXCollections.observableArrayList(storetemp.getAllProducts());
        System.out.println(productos);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("principalclientes.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = loader.load();
        AppController controller = loader.getController();
        int numColumns = 3;
        for (int i = 0; i < productos.size(); i++) {
            FXMLLoader loaderProductView = new FXMLLoader(getClass().getResource("productviewclient.fxml"));
            Node node = loaderProductView.load();
            ProductViewClientController controllerProductView = loaderProductView.getController();
            controllerProductView.setProduct(productos.get(i));
            int row = i / numColumns;
            int col = i % numColumns;
            controller.espacioProducto.add(node, col, row + 3 );
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    public void irAPrincipalVendedor(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("principalvendedor.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

    public void irAAñadirProducto(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("añadirproducto.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void irAInicioSesion(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("iniciodesesion.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // manipulacion de escenas
    public void reemplazarRecursos(nodeProduct product) {
        Image image = new Image(product.urlImage);
        añadirImagen.setImage(image);
        idHistorieta.setText(product.idProduct);
        precioLabel.setText(String.valueOf(product.price));
        nombreHistorieta.setText(product.name);
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

    public void listarCategorias(Event event) {
        llenarCombo(categoriaProducto, categorias);
    }


    //Manipulacion de productos.
    @FXML
    private void handleSelectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                File destDir = new File("comics");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                File destFile = new File(destDir, selectedFile.getName());
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                selectedImagePath = destFile.toURI().toString();
                Image image = new Image(selectedImagePath);
                añadirImagen.setImage(image);
            } catch (IOException e) {
                showErrorAlert("Error al copiar la imagen", "No se pudo copiar la imagen seleccionada a la carpeta designada.");
                e.printStackTrace();
            }
        }
    }


    //Metodos redundantes
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void actualizarEscenas(nodeUser user) throws IOException {
        String fxmlPath;

        if (user instanceof nodeSeller) {
            fxmlPath = "principalvendedor2.fxml";
        } else if (user instanceof nodeClient) {
            fxmlPath = "principalclientes.fxml";
        } else {
            mostrarError("Error de inicio de sesión", "Usuario inválido", "No se reconoce el tipo de usuario.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        AppController controller = loader.getController();
        controller.setStore(storetemp);
        Scene scene = new Scene(root);
        Stage stage = (Stage) idUsuario.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}

