package com.example.proyectocarritodecompras;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class store {

    nodeUser cabUser;
    nodeProduct cabProduct;
    purchaseHistory pHIstory;

    store() {
        cabUser = null;
        cabProduct = null;
        purchaseHistory pHistory = new purchaseHistory();

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
}
