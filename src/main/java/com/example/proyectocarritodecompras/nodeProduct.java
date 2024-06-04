package com.example.proyectocarritodecompras;

public class nodeProduct {
    String urlImage;
    String idProduct;
    String name;
    String desc;
    String category;
    int quantity;
    double price;
    nodeProduct sig, ant;

    public nodeProduct(String urlImage, String idProduct, String name, String desc, int quantity, double price, String category) {
        this.urlImage = urlImage;
        this.idProduct = idProduct;
        this.name = name;
        this.desc = desc;
        this.quantity = quantity;
        this.price = price;
        this.ant = null;
        this.sig = null;
        this.category = category;
    }
}
