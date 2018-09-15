package com.msf.bakingtime.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.msf.bakingtime.dao.IngredientDao;
import com.msf.bakingtime.dao.RecipeDao;
import com.msf.bakingtime.model.Ingredient;
import com.msf.bakingtime.model.Recipe;

@Database(entities = {Recipe.class, Ingredient.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class RecipeDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipes";
    private static RecipeDatabase sInstance;

    public static RecipeDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RecipeDatabase.class, RecipeDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
}