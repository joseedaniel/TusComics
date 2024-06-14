package com.example.proyectocarritodecompras;

public class shoppingCart {



    nodeProduct cab;
    nodeProduct fin;
    int size;

    public shoppingCart() {
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

    public nodeProduct remove() {
        if (size == 0) {
            return null;
        }
        nodeProduct removedProduct = fin;
        if (size == 1) {
            cab = null;
            fin = null;
        } else {
            fin = fin.ant;
            fin.sig = cab;
            cab.ant = fin;
        }
        size--;
        return removedProduct;
    }

    public int getSize() {
        return size;
    }

}

