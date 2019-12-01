package com.example.chellenge_ksp.RecyclerViewResult;

public class ItemResult {
    private int imageResource;
    private String name_user;
    private String points;

    public ItemResult(int imageResource, String name_user, String points){
        this.imageResource = imageResource;
        this.name_user = name_user;
        this.points = points;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName_user() {
        return name_user;
    }

    public String getPoints() {
        return points;
    }
}
