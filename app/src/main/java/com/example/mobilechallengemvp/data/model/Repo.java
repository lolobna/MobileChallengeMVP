package com.example.mobilechallengemvp.data.model;
import com.google.gson.annotations.SerializedName;

public class Repo {

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("stargazers_count")
    public int stars;

    @SerializedName("owner")
    public Owner owner;



    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getStargazersCount() { return stars; }
    public Owner getOwner() { return owner; }
}