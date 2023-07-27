package com.example.e_troll.ModelClass;

public class FineModelClass {


    String id;
    String date;
    String fine;

    String status;

    public FineModelClass(String id, String date, String fine, String status) {
        this.id = id;
        this.date = date;
        this.fine = fine;
        this.status = status;

    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getFine() {
        return fine;
    }

    public String getStatus() {return status;}

}