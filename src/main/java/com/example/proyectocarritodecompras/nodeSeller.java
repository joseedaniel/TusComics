package com.example.proyectocarritodecompras;

public class nodeSeller extends nodeUser{
    boolean typeSeller;
    nodeProduct soldProducts;

    public nodeSeller(String idLog, String pass, boolean typeSeller) {
        super(idLog, pass);
        this.typeSeller = typeSeller;
        this.soldProducts = null;
    }
}
