package com.example.proyectocarritodecompras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class shoppingCart {

    private List<nodeProduct> cart;

    public shoppingCart() {
        this.cart = new ArrayList<>();
    }

    public void add(nodeProduct product) {
        this.cart.add(product);
    }

    public nodeProduct remove() {
        if (cart.isEmpty()) {
            return null;
        }
        return cart.remove(cart.size() - 1); // Remove the last added product (LIFO behavior)
    }

    public void serialize(String filename, String idLog) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (nodeProduct product : cart) {
                writer.write(String.format("%s,%s,%s,%s,%s,%d,%.2f,%s\n",
                        product.urlImage, product.idProduct, product.name,
                        product.desc, product.category, product.quantity,
                        product.price, idLog));
            }
        }
    }

    public static shoppingCart deserialize(String filename, String idLog) throws IOException {
        shoppingCart cart = new shoppingCart();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8 && parts[7].equals(idLog)) {
                    nodeProduct product = new nodeProduct(parts[0], parts[1], parts[2],
                            parts[3], parts[4], Integer.parseInt(parts[5]),
                            Double.parseDouble(parts[6]));
                    cart.add(product);
                }
            }
        }
        return cart;
    }
}
