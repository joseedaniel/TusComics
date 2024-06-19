package com.example.proyectocarritodecompras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class nodeSeller extends nodeUser {
    boolean typeSeller;
    nodeProduct soldProducts;

    public nodeSeller(String idLog, String pass, boolean typeSeller) {
        super(idLog, pass);
        this.typeSeller = typeSeller;
        this.soldProducts = null;
    }

    public void addSoldProduct(nodeProduct product) {
        if (soldProducts == null) {
            soldProducts = new nodeProduct();
            soldProducts = product;
            soldProducts.ant = soldProducts;
            soldProducts.sig = soldProducts;
        } else {
            nodeProduct first = soldProducts.sig;
            soldProducts.sig = product;
            first.ant = product;
            product.ant = soldProducts;
            product.sig = first;
        }
    }

    public nodeProduct createProduct(String urlImage, String idProduct, String name, String desc, String category, int quantity, double price) {
        return new nodeProduct(urlImage, idProduct, name, desc, category, quantity, price);
    }

    public void editProduct(nodeProduct product, String urlImage, String idProduct, String name, String desc, String category, int quantity, double price) {
        product.urlImage = urlImage;
        product.idProduct = idProduct;
        product.name = name;
        product.desc = desc;
        product.category = category;
        product.quantity = quantity;
        product.price = price;
    }

    public void deleteProduct(nodeProduct product) {
        if (soldProducts == null) return;

        nodeProduct current = soldProducts;
        do {
            if (current == product) {
                if (current == soldProducts && current.sig == soldProducts) {
                    soldProducts = null;
                } else {
                    current.ant.sig = current.sig;
                    current.sig.ant = current.ant;
                    if (current == soldProducts) {
                        soldProducts = current.sig;
                    }
                }
                break;
            }
            current = current.sig;
        } while (current != soldProducts);
    }

    public boolean isSoldProductsEmpty() {
        return soldProducts == null;
    }

    public void serializeSoldProducts(BufferedWriter writer) throws IOException {
        if (soldProducts != null) {
            nodeProduct current = soldProducts;
            do {
                current.serialize(writer);
                current = current.sig;
            } while (current != soldProducts);
        }
    }

    public static nodeProduct deserializeSoldProducts(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line == null || line.isEmpty()) return null;
        String[] parts = line.split(",");
        return new nodeProduct(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), Double.parseDouble(parts[6]));
    }

}
