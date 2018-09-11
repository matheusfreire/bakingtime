package com.msf.bakingtime.network;

import com.msf.bakingtime.model.Recipe;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeEndPoint {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<LinkedList<Recipe>> fetchRecipes();
}
