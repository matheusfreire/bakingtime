package com.msf.bakingtime.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Ingredient implements Comparable<Ingredient> {

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    @Override
    public int compareTo(@NonNull Ingredient ingredient) {
        return this.quantity - ingredient.quantity;
    }
}
