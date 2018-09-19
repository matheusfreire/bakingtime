package com.msf.bakingtime.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
@Entity(tableName = "recipes")
public class Recipe implements Parcelable {

    @SerializedName("id")
    @PrimaryKey
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    @Ignore
    private List<Ingredient> ingredients;

    @SerializedName("steps")
    @Ignore
    private List<Step> steps;

    @Ignore
    public Recipe(){

    }

    public Recipe(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    protected Recipe(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }
}
