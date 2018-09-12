package com.msf.bakingtime.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.msf.bakingtime.model.Recipe;

import java.util.LinkedList;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeViewModel extends ViewModel {

    @Setter
    private Call<LinkedList<Recipe>> mFetchRecipes;

    @Getter
    private MutableLiveData<LinkedList<Recipe>> mutableLiveDataRecipes;


    RecipeViewModel(Call<LinkedList<Recipe>> mCallRecipes) {
        setMFetchRecipes(mCallRecipes);
        initMultable();
        fetchRecipes();
    }

    private void initMultable() {
        if (mutableLiveDataRecipes == null) {
            mutableLiveDataRecipes = new MutableLiveData<>();
        }
    }

    private void fetchRecipes() {
        mFetchRecipes.enqueue(new Callback<LinkedList<Recipe>>() {
            @Override
            public void onResponse(Call<LinkedList<Recipe>> call, Response<LinkedList<Recipe>> response) {
                mutableLiveDataRecipes.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LinkedList<Recipe>> call, Throwable t) {
                mutableLiveDataRecipes.postValue(null);
            }
        });
    }
}
