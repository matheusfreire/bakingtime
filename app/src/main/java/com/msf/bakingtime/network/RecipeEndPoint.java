package com.msf.bakingtime.network;

import com.msf.bakingtime.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeEndPoint {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> callRecipes();
}
