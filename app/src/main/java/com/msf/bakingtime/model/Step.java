package com.msf.bakingtime.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Step implements Comparable<Step>{

    @SerializedName("id")
    private long id;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("description")
    private String description;

    @SerializedName("videoURL")
    private String videoUrl;

    @SerializedName("thumbnailURL")
    private String thumbnailUrl;

    @Override
    public int compareTo(@NonNull Step step) {
        return (int) (this.id - step.id);
    }
}
