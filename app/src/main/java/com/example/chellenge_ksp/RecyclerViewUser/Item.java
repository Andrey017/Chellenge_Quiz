package com.example.chellenge_ksp.RecyclerViewUser;

public class Item {
    private String name;
    private int imageResource;

    public Item(String name, int imageResource){
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getNameUser() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
