package com.example.proyectocarritodecompras;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;

public class store {

    nodeUser cabUser;
    nodeProduct cabProduct;

    store() {
        cabUser = null;
        cabProduct = null;
    }

    public void setAddClient(nodeClient info) {
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

    public void setAddProduct(nodeProduct info) {
        if (cabProduct == null) {
            cabProduct = info;
            info.sig = info.ant = info;
        } else {
            nodeProduct ultimo = cabProduct.ant;
            info.sig = cabProduct;
            info.ant = ultimo;
            cabProduct.ant = info;
            ultimo.sig = info;
        }
    }

    nodeUser getBuscarIdUser(String idLog) {
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

    public void addProductToClientCart(nodeClient client, nodeProduct product) {
        client.addProductToCart(product);
    }

    public nodeProduct removeProductFromClientPurchaseHistory(nodeClient client) {
        return client.removeProductFromCart();
    }

    public void addProductToClientWishlist(nodeClient client, nodeProduct product) {
        client.addProductToWishlist(product);
    }

    public nodeProduct removeProductFromClientWishlist(nodeClient client) {
        return client.removeProductFromWishlist();
    }

    public boolean isClientWishlistEmpty(nodeClient client) {
        return client.isWishlistEmpty();
    }

    public void buyProductFromClientCart(nodeClient client) {
        client.buyItem();
    }

    public void addProductToSellerSoldProducts(nodeSeller seller, nodeProduct product) {
        seller.addSoldProduct(product);
    }

    public void saveAllProducts(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        nodeProduct temp = cabProduct;
        while (temp != null) {
            temp.serialize(writer);
            temp = temp.sig;
        }
        writer.close();
    }

    public void saveAllClients() throws IOException {
        nodeUser temp = cabUser;
        if (temp != null) {
            do {
                if (temp instanceof nodeClient) {
                    ((nodeClient) temp).saveClientData();
                }
                temp = temp.sig;
            } while (temp != cabUser);
        }
    }

    public void loadAllClients() throws IOException {
        nodeUser temp = cabUser;
        if (temp != null) {
            do {
                if (temp instanceof nodeClient) {
                    ((nodeClient) temp).loadClientData();
                }
                temp = temp.sig;
            } while (temp != cabUser);
        }
    }

    public void saveAllSellers(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        nodeUser temp = cabUser;
        if (temp != null) {
            do {
                if (temp instanceof nodeSeller) {
                    ((nodeSeller) temp).serializeSoldProducts(writer);
                }
                temp = temp.sig;
            } while (temp != cabUser);
        }
        writer.close();
    }

    public void loadAllSellers(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            nodeSeller seller = (nodeSeller) getBuscarIdUser(line);
            if (seller != null) {
                ((nodeSeller) seller).deserializeSoldProducts(reader);
            }
        }
        reader.close();
    }

    public void loadAllProducts(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            nodeProduct product = new nodeProduct(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), Double.parseDouble(parts[6]));
        }
        reader.close();
    }

    public void addProduct(nodeSeller seller, nodeProduct product) {
        if (seller.typeSeller) {
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
        } else {
            throw new IllegalArgumentException("Solo los vendedores pueden agregar productos.");
        }
    }

    public void editProduct(nodeSeller seller, nodeProduct product, String urlImage, String idProduct, String name, String desc, String category, int quantity, double price) {
        if (seller.typeSeller) {
            seller.editProduct(product, urlImage, idProduct, name, desc, category, quantity, price);
        } else {
            throw new IllegalArgumentException("Solo los vendedores pueden editar productos.");
        }
    }

    public void deleteProduct(nodeSeller seller, nodeProduct product) {
        if (seller.typeSeller) {
            seller.deleteProduct(product);
        } else {
            throw new IllegalArgumentException("Solo los vendedores pueden eliminar productos.");
        }
    }

}
