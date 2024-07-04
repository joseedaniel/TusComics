package com.example.proyectocarritodecompras;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class nodeProduct {
    String urlImage;
    String idProduct;
    String name;
    String desc;
    String category;
    int quantity;
    double price;
    nodeProduct sig, ant;

    public nodeProduct() {
        urlImage = "";
        idProduct = "";
        name = "";
        desc = "";
        category = "";
        quantity = 0;
        price = 0;
    }

    public nodeProduct(String urlImage, String idProduct, String name, String desc, String category, int quantity, double price) {
        this.urlImage = urlImage;
        this.idProduct = idProduct;
        this.name = name;
        this.desc = desc;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.sig = null;
        this.ant = null;
    }

    public void serialize(BufferedWriter writer) throws IOException {
        writer.write(String.format("%s,%s,%s,%s,%s,%d,%.2f\n", urlImage, idProduct, name, desc, category, quantity, price));
    }

    public static nodeProduct deserialize(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line == null || line.isEmpty()) return null;
        String[] parts = line.split(",", -1); // Use -1 to preserve empty strings
        return new nodeProduct(
                parts[0],           // urlImage
                parts[1],           // idProduct
                parts[2],           // name
                parts[3],           // desc
                parts[4],           // category
                Integer.parseInt(parts[5]),    // quantity
                Double.parseDouble(parts[6])    // price
        );
    }
}
