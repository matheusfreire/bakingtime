package com.msf.bakingtime.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.msf.bakingtime.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> loadRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(Recipe... recipes);

    @Delete
    void deleteRecipe(Recipe recipe);

}