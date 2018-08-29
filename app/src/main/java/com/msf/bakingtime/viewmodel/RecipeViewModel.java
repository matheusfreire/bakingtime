package com.msf.bakingtime.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.msf.bakingtime.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeViewModel extends AndroidViewModel {

    @Getter
    private MutableLiveData<List<Recipe>> mutableLiveDataRecipes;


    public RecipeViewModel(@NonNull Application application, Call<ArrayList<Recipe>> mCallRecipes) {
        super(application);
        if (mutableLiveDataRecipes == null) {
            mutableLiveDataRecipes = new MutableLiveData<>();
        }
        getRecipesFromNet(mCallRecipes);
    }

    private void getRecipesFromNet(Call<ArrayList<Recipe>> mCallRecipes) {
        mCallRecipes.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                mutableLiveDataRecipes.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                mutableLiveDataRecipes.postValue(null);
            }
        });
    }
}
