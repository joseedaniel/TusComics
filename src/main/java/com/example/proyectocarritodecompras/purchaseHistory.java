package com.example.proyectocarritodecompras;

import java.io.*;

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

    public static purchaseHistory deserialize(String filename) throws IOException {
        purchaseHistory history = new purchaseHistory();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        nodeProduct product;
        while ((product = nodeProduct.deserialize(reader)) != null) {
            history.add(product);
        }
        reader.close();
        return history;
    }

}
