package com.msf.bakingtime.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.msf.bakingtime.model.Recipe;

import java.util.LinkedList;

import retrofit2.Call;

public class RecipeListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Call<LinkedList<Recipe>> mFetchRecipe;
    private Application application;

    public RecipeListViewModelFactory(Application application, Call<LinkedList<Recipe>> callRecipes){
        this.application = application;
        this.mFetchRecipe = callRecipes;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeViewModel(application,mFetchRecipe);
    }
}