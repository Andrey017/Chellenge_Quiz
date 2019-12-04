package com.example.chellenge_ksp.RecyclerViewResult;

public class ItemResult {
    private String name_user;
    private String points;

    public ItemResult(String name_user, String points){
        this.name_user = name_user;
        this.points = points;
    }

    public String getName_user() {
        return name_user;
    }

    public String getPoints() {
        return points;
    }
}
