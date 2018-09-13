package com.msf.bakingtime.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.msf.bakingtime.model.Recipe;

import java.util.List;

import lombok.Setter;

@Dao
public abstract class RecipeDao {

    @Setter
    private IngredientDao ingredientDao;

    @Query("SELECT * FROM recipes")
    public abstract LiveData<List<Recipe>> loadRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertRecipe(Recipe recipe);

    @Transaction
    public void insertRecipeAndIngredients(Recipe recipe){
        insertRecipe(recipe);
        ingredientDao.insertIngredients(recipe.getIngredients());
    }

    @Delete
    abstract void deleteRecipe(Recipe recipe);

}