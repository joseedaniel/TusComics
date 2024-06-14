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

    public nodeProduct (){
        urlImage = "";
        idProduct = "";
        name = "";
        desc = "";
        category = "";
        quantity = 0;
        price = 0;
        sig = ant = null;
    }
}
