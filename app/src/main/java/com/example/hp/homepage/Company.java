package com.example.hp.homepage;

public class Company {
    private String title;
    private String description;
    private float mincg;

    public Company(){

    }
    public Company(String title, String description, float mincg) {
        this.title = title;
        this.description = description;
        this.mincg = mincg;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getMincg() {
        return mincg;
    }

}
