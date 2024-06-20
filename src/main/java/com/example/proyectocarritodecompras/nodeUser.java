package com.example.proyectocarritodecompras;

public class nodeUser {
    protected String idLog;
    protected String pass;
    protected nodeUser sig, ant;

    public nodeUser() {
        idLog = "";
        pass = "";
        sig = null;
        ant = null;
    }

    public nodeUser(String idLog, String pass) {
        this.idLog = idLog;
        this.pass = pass;
        this.sig = null;
        this.ant = null;
    }

    public String getIdLog() {
        return idLog;
    }

    public String getPassword() {
        return pass;
    }
}
