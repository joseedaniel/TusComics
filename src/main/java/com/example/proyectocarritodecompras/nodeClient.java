package com.example.proyectocarritodecompras;

import java.io.IOException;

public class nodeClient extends nodeUser {

    String nam;
    String mail;
    int tel;
    String country;
    String dep;
    String mun;
    String neigh;
    String address;
    int pstCode;
    String notes;
    shoppingCart cart;
    wishList wishList;
    purchaseHistory history;

    public nodeClient() {
        super();
        nam = "";
        mail = "";
        tel = 0;
        country = "";
        dep = "";
        mun = "";
        neigh = "";
        pstCode = 0;
        notes = "";
        cart = new shoppingCart();
        wishList = new wishList();
        history = new purchaseHistory();
    }

    public nodeClient(String idLog, String pass) {
        super(idLog, pass);
        this.idLog = idLog;
        this.pass = pass;
        cart = new shoppingCart();
        wishList = new wishList();
        history = new purchaseHistory();
    }

    public void addProductToCart(nodeProduct product) {
        this.cart.add(product);
    }

    public nodeProduct removeProductFromCart() {
        return this.cart.remove();
    }

    public void saveClientData() throws IOException {
        cart.serialize("shoppingcart.txt", getIdLog());
    }

    public void loadClientData() throws IOException {
        cart = shoppingCart.deserialize("shoppingcart.txt", getIdLog());
    }
}
