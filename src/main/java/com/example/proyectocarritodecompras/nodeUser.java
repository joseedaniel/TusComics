package com.example.proyectocarritodecompras;

public class nodeUser {
    String idLog;
    String pass;
    nodeUser sig, ant;

    public nodeUser(String idLog, String pass) {
        this.idLog = idLog;
        this.pass = pass;
        this.sig = null;
        this.ant = null;
    }
}
