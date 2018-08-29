package com.msf.bakingtime.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.msf.bakingtime.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class RecipeListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Call<ArrayList<Recipe>> mCallRecipes;
    private Application application;

    public RecipeListViewModelFactory(Call<ArrayList<Recipe>> callRecipes, Application application){
        this.mCallRecipes = callRecipes;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeViewModel(application,mCallRecipes);
    }
}