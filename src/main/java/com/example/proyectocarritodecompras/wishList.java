package com.example.proyectocarritodecompras;

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

    public nodeProduct peek() {
        return cab;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

}
