package com.example.seungmin1216.team.Map;

import java.util.ArrayList;

public class Item {
    private Meta meta;
    private ArrayList<Documentss> documents;

    public Item() {


    }

    public Item(Meta meta, ArrayList<Documentss> documents) {
        this.meta = meta;
        this.documents = documents;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ArrayList<Documentss> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Documentss> documents) {
        this.documents = documents;
    }
}
