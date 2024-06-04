package com.example.proyectocarritodecompras;

public class nodeClient extends nodeUser{
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
    nodeProduct productWishList;
    nodeProduct productShoppingCart;
    nodeProduct productpurchaseHistory;



    public nodeClient(String idLog, String pass, String nam, String mail, int tel, String country, String dep, String mun, String neigh, String address, int pstCode, String notes) {
        super(idLog, pass);
        this.nam = nam;
        this.mail = mail;
        this.tel = tel;
        this.country = country;
        this.dep = dep;
        this.mun = mun;
        this.neigh = neigh;
        this.address = address;
        this.pstCode = pstCode;
        this.notes = notes;
        this.productpurchaseHistory = null;
        this.productWishList = null;
        this.productShoppingCart = null;
    }
}
