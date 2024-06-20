package com.example.proyectocarritodecompras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class nodeSeller extends nodeUser {
    boolean typeSeller;
    nodeProduct soldProducts;

    public nodeSeller() {
        super();
        typeSeller = false;
        soldProducts = null;
    }

    public nodeSeller(String idLog, String pass) {
        super(idLog, pass);
        typeSeller = true;
        soldProducts = null;
    }

    public void addSoldProduct(nodeProduct product) {
        if (soldProducts == null) {
            soldProducts = product;
            soldProducts.sig = soldProducts.ant = product;
        } else {
            nodeProduct first = soldProducts;
            nodeProduct last = soldProducts.ant;
            last.sig = product;
            product.ant = last;
            product.sig = first;
            first.ant = product;
            soldProducts = product;
        }
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
                if (current.sig == current) {
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
