package com.msf.bakingtime.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.msf.bakingtime.model.Recipe;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeViewModel extends AndroidViewModel {


    private Call<LinkedList<Recipe>> mFetchRecipes;

    @Getter
    private MutableLiveData<LinkedList<Recipe>> mutableLiveDataRecipes;


    RecipeViewModel(@NonNull Application application, Call<LinkedList<Recipe>> mCallRecipes) {
        super(application);
        this.mFetchRecipes = mCallRecipes;
        if (mutableLiveDataRecipes == null) {
            mutableLiveDataRecipes = new MutableLiveData<>();
        }
        fetchRecipes();
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
