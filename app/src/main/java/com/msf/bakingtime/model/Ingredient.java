package com.msf.bakingtime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@Entity(tableName = "ingredients",foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipe_id"))
public class Ingredient implements Parcelable {

    private static final String FREE_SPACE = " ";
    public static final String KEY_INGREDIENT = "ingredient";
    public static final String KEY_MEASURE = "measure";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_HASHID = "hashId";
    @PrimaryKey
    @NonNull
    private String hashId;

    @SerializedName("quantity")
    private double quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String ingredient;

    @ColumnInfo(name = "recipe_id")
    public long recipeId;


    public Ingredient(double quantity, String measure, String ingredient, long recipeId) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.recipeId = recipeId;
    }

    @Ignore
    public Ingredient(Cursor cursor){
        setIngredient(cursor.getString(cursor.getColumnIndex(KEY_INGREDIENT)));
        setMeasure(cursor.getString(cursor.getColumnIndex(KEY_MEASURE)));
        setQuantity(cursor.getDouble(cursor.getColumnIndex(KEY_QUANTITY)));
        setHashId(cursor.getString(cursor.getColumnIndex(KEY_HASHID)));
    }

    @Ignore
    public Ingredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
        recipeId = in.readLong();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
        dest.writeLong(recipeId);
    }

    public String buildText() {
        return new StringBuilder().append(this.getQuantity()).append(FREE_SPACE)
                .append(this.getMeasure()).append(FREE_SPACE)
                .append(this.getIngredient()) .toString();
    }
}
