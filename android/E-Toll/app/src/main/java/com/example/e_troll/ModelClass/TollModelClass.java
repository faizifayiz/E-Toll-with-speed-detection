package com.example.e_troll.ModelClass;

public class TollModelClass {

    String id;
    String tid;
    String date;
    String time;
    String boothname;
    String tollamount;
    String status;



    public TollModelClass(String id,String tid, String date, String time, String boothname, String tollamount, String status) {
        this.id = id;
        this.tid = tid;
        this.date = date;
        this.time = time;
        this.boothname = boothname;
        this.tollamount = tollamount;
        this.status = status;

    }

    public String getId() {
        return id;
    }

    public String getTid() {
        return tid;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getBoothname() {return boothname;}

    public String getTollamount() {return tollamount;}

    public String getStatus() {return status;}
}
