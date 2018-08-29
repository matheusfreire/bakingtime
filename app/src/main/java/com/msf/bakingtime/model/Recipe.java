package com.msf.bakingtime.model;

import com.google.gson.annotations.SerializedName;

import java.util.TreeSet;

import lombok.Data;

@Data
public class Recipe {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private TreeSet<Ingredient> ingredients;

    @SerializedName("steps")
    private TreeSet<Step> steps;
}
