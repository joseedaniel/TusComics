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

    public void addProductToWishlist(nodeProduct product) {
        this.wishList.add(product);
    }

    public nodeProduct removeProductFromWishlist() {
        return this.wishList.remove();
    }

    public boolean isWishlistEmpty() {
        return this.wishList.isEmpty();
    }

    public void buyItem() {
        nodeProduct product = this.cart.remove();
        if (product != null) {
            this.history.add(product);
        }
    }

    public void saveClientData() throws IOException {
        cart.serialize(idLog + "_cart.txt");
        wishList.serialize(idLog + "_wishlist.txt");
        history.serialize(idLog + "_history.txt");
    }

    public void loadClientData() throws IOException {
        cart = shoppingCart.deserialize(idLog + "_cart.txt");
        wishList = wishList.deserialize(idLog + "_wishlist.txt");
        history = purchaseHistory.deserialize(idLog + "_history.txt");
    }
}
