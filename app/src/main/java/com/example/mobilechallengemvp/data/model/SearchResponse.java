package com.example.mobilechallengemvp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("items")
    public List<Repo> items;

    public List<Repo> getItems() { return items; }
}