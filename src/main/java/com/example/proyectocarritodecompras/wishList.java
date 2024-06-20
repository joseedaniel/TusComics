package com.example.proyectocarritodecompras;

import java.io.*;

public class wishList {

    nodeProduct cab;
    nodeProduct fin;
    int size;

    public wishList() {
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
        nodeProduct removedNode = cab;
        if (size == 1) {
            cab = null;
            fin = null;
        } else {
            cab = cab.sig;
            cab.ant = fin;
            fin.sig = cab;
        }
        size--;
        removedNode.sig = null;
        removedNode.ant = null;
        return removedNode;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void serialize(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        nodeProduct current = cab;
        if (current != null) {
            do {
                current.serialize(writer);
                current = current.sig;
            } while (current != cab);
        }
        writer.close();
    }

    public static wishList deserialize(String filename) throws IOException {
        wishList wishlist = new wishList();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        nodeProduct product;
        while ((product = nodeProduct.deserialize(reader)) != null) {
            wishlist.add(product);
        }
        reader.close();
        return wishlist;
    }

}
