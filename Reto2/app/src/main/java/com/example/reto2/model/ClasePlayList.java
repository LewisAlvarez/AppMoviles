package com.example.reto2.model;

import java.util.ArrayList;

public class ClasePlayList {

    ArrayList<PlayList> data;
    String next;

    public ClasePlayList() {
    }

    public ArrayList<PlayList> getData() {
        return data;
    }

    public void setData(ArrayList<PlayList> data) {
        this.data = data;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
