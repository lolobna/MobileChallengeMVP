package com.example.mobilechallengemvp.data.model;

import com.google.gson.annotations.SerializedName;

public class Owner {
    @SerializedName("login")
    public String username;
    @SerializedName("avatar_url")
    public String avatar;
    public String getUsername() { return username; }
    public String getAvatar() { return avatar; }
}
