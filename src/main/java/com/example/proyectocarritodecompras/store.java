package com.example.proyectocarritodecompras;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;

public class store {

    private nodeUser cabUser;
    private nodeProduct cabProduct;
    private static store instance;
    private nodeUser currentUser;


    store() {
        cabUser = null;
        cabProduct = null;
    }

    //Listas principales
    public void setAddClient(nodeClient info) {
        if (cabUser == null) {
            cabUser = info;
            info.sig = info.ant = info;
        } else {
            info.sig = cabUser.sig;
            info.ant = cabUser;
            cabUser.sig.ant = info;
            cabUser.sig = info;
            cabUser = info;
        }
    }

    public void setAddSeller(nodeSeller info) {
        if (cabUser == null) {
            cabUser = info;
            info.sig = info.ant = info;
        } else {
            nodeUser ultimo = cabUser.ant;
            info.sig = cabUser;
            info.ant = ultimo;
            cabUser.ant = info;
            ultimo.sig = info;
        }
    }

    public void addProduct(nodeProduct product) {
        if (cabProduct == null) {
            cabProduct = product;
            product.sig = product.ant = product;
        } else {
            nodeProduct last = cabProduct.ant;
            last.sig = product;
            product.ant = last;
            product.sig = cabProduct;
            cabProduct.ant = product;
        }
    }


    //Busqueda por ID
    public nodeUser getBuscarIdUser(String idLog) {
        if (cabUser == null) {
            return null;
        }
        nodeUser temp = cabUser;
        do {
            if (temp.idLog.equalsIgnoreCase(idLog)) {
                return temp;
            }
            temp = temp.sig;
        } while (temp != cabUser);
        return null;
    }

    public boolean isUserExists(String idLog) {
        if (cabUser == null) {
            return false;
        }
        nodeUser temp = cabUser;
        do {
            if (temp.idLog.equalsIgnoreCase(idLog)) {
                return true;
            }
            temp = temp.sig;
        } while (temp != cabUser);
        return false;
    }

    public nodeProduct getProductById(String id) {
        if (cabProduct == null) {
            return null;
        }

        nodeProduct temp = cabProduct;
        do {
            if (temp.idProduct.equals(id)) {
                return temp;
            }
            temp = temp.sig;
        } while (temp != cabProduct);

        return null;
    }


    //Manipulacion de archivos
    public void saveAllProducts(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        nodeProduct temp = cabProduct.sig;
        if (temp != null) {
            do {
                String info = temp.urlImage + "," + temp.idProduct + "," + temp.name + "," + temp.desc + "," + temp.category + "," + temp.quantity + "," + temp.price;
                writer.write(info);
                writer.newLine();
                System.out.println(info);

                temp = temp.sig;
            } while (temp != cabProduct.sig);
        }
        writer.close();
    }

    public void loadAllProducts(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String urlImage = parts[0].trim();
                    String idProduct = parts[1].trim();
                    String name = parts[2].trim();
                    String desc = parts[3].trim();
                    String category = parts[4].trim();
                    int quantity = Integer.parseInt(parts[5].trim());
                    double price = Double.parseDouble(parts[6].trim());

                    nodeProduct product = new nodeProduct(urlImage, idProduct, name, desc, category, quantity, price);
                    addProduct(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllClients(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        nodeUser temp = cabUser.sig;
        if (temp != null) {
            do {
                String info = temp.idLog + ":" + temp.pass;
                writer.write(info);
                writer.newLine();
                //System.out.println(info);

                temp = temp.sig;
            } while (temp != cabUser.sig);
        }
        writer.close();
    }

    public void loadAllClients(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    String id = parts[0].trim();
                    String password = parts[1].trim();
                    nodeClient client = new nodeClient(id, password);
                    setAddClient(client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllSellers(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        nodeUser temp = cabUser.sig;
        if (temp != null) {
            do {
                String info = temp.idLog + ":" + temp.pass;
                writer.write(info);
                writer.newLine();
                temp = temp.sig;
            } while (temp != cabUser.sig);
        }
        writer.close();
    }

    public void loadAllSellers(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    String id = parts[0].trim();
                    String password = parts[1].trim();
                    nodeSeller seller = new nodeSeller(id, password);
                    setAddSeller(seller);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Manipulacion de listas auxiliares

//    public void addToCart(nodeProduct product) {
//        if (currentUser != null && currentUser instanceof nodeClient) {
//            ((nodeClient) currentUser).addToCart(product);
//        }
//    }
//
//    public void removeFromCart(nodeProduct product) {
//        if (currentUser != null && currentUser instanceof nodeClient) {
//            ((nodeClient) currentUser).removeFromCart(product);
//        }
//    }
//
//    public ObservableList<nodeProduct> getCartItems() {
//        if (currentUser != null && currentUser instanceof nodeClient) {
//            return ((nodeClient) currentUser).getCartItems();
//        }
//        return FXCollections.observableArrayList();
//    }
//
//    public void clearCart() {
//        if (currentUser != null && currentUser instanceof nodeClient) {
//            ((nodeClient) currentUser).removeProductFromCart();
//        }
//    }


    //Identificadores
    public static store getInstance() {
        if (instance == null) {
            instance = new store();
        }
        return instance;
    }

    public void login(String id, String password) {
        if (isUserExists(id)) {
            if (esComprador(id, password)) {
                nodeUser user = getBuscarIdUser(id);
                login(user);
            } else if (esVendedor(id, password)) {
                nodeUser user = getBuscarIdUser(id);
                login(user);
            } else {
                mostrarError("Contraseña incorrecta.");
            }
        } else {
            mostrarError("Usuario no encontrado.");
        }
    }

    public void login(nodeUser user) {
        this.currentUser = user;
    }

    public void logout() {
        this.currentUser = null;
    }

    public nodeUser getCurrentUser() {
        return currentUser;
    }

    private boolean esComprador(String id, String password) {
        nodeUser user = getBuscarIdUser(id);
        return user instanceof nodeClient && user.getPassword().equals(password);
    }

    private boolean esVendedor(String id, String password) {
        nodeUser user = getBuscarIdUser(id);
        return user instanceof nodeSeller && user.getPassword().equals(password);
    }

    public void registrarUsuario(String idLog, String password, boolean esComprador) {

        if (esComprador) {
            nodeClient nuevoCliente = new nodeClient(idLog, password);
            setAddClient(nuevoCliente);
        } else {
            nodeSeller nuevoVendedor = new nodeSeller(idLog, password);
            setAddSeller(nuevoVendedor);
            try {
                saveAllSellers("sellers.txt");
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al guardar el vendedor en el archivo.");
            }
        }
    }

    public ObservableList<nodeProduct> getAllProducts() {
        ObservableList<nodeProduct> productList = FXCollections.observableArrayList();

        // Iterar sobre la lista de productos y añadirlos a productList
        nodeProduct temp = cabProduct;
        if (temp != null) {
            do {
                productList.add(temp);
                temp = temp.sig;
            } while (temp != cabProduct);
        }

        return productList;
    }


    


    //Metodos redundantes
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
