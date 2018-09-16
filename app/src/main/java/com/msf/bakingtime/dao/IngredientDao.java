package com.msf.bakingtime.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.msf.bakingtime.model.Ingredient;
import com.msf.bakingtime.model.Recipe;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredients where recipe_id = :recipeId")
    LiveData<List<Ingredient>> loadIngredients(long recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredients(List<Ingredient> ingredients);

    @Query("DELETE FROM ingredients")
    void deleteIngredients();
}