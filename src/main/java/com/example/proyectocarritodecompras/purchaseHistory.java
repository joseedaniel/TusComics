package com.example.proyectocarritodecompras;

public class purchaseHistory {

    nodeProduct cab;
    nodeProduct fin;
    int size;

    public purchaseHistory() {
        cab = null;
        fin = null;
        size = 0;

    }

    public void add(nodeProduct info) {
        if (cab == null) {
            cab = info;
            fin = info;
            cab.sig = cab;
            cab.ant = cab;
        } else {
            fin.sig = info;
            info.ant = fin;
            info.sig = cab;
            cab.ant = info;
            fin = info;
        }
        size++;
    }

    public int getSize() {
        return size;
    }

}
