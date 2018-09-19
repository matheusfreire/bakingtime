package com.msf.bakingtime.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.msf.bakingtime.R;
import com.msf.bakingtime.db.RecipeDatabase;
import com.msf.bakingtime.model.Ingredient;
import com.msf.bakingtime.model.Recipe;
import com.msf.bakingtime.network.RecipeEndPoint;
import com.msf.bakingtime.network.RetrofitClientInstance;
import com.msf.bakingtime.util.AppExecutor;
import com.msf.bakingtime.util.Delayer;
import com.msf.bakingtime.viewmodel.RecipeListViewModelFactory;
import com.msf.bakingtime.viewmodel.RecipeViewModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListRecipesFragment extends FragmentsRecipe implements Delayer.DelayerCallback{

    @BindView(R.id.recipe_list)
    RecyclerView mRecyclerViewRecipes;


    @BindView(R.id.progress_loading)
    ProgressBar mProgressLoading;

    @BindView(R.id.error_message)
    TextView mErrorMessage;

    private RecipeViewModel recipeViewModel;

    private RecipeDatabase database;

    public ListRecipesFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_recipes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecipeEndPoint endPoint = RetrofitClientInstance.getInstance().create(RecipeEndPoint.class);
        RecipeListViewModelFactory factory = new RecipeListViewModelFactory(endPoint.fetchRecipes());
        recipeViewModel = ViewModelProviders.of(this, factory).get(RecipeViewModel.class);
        mProgressLoading.setVisibility(View.VISIBLE);
        mRecyclerViewRecipes.setVisibility(View.INVISIBLE);
        if(hasConnection()){
            observableFromVM();
        } else {
            treatNetworkOffline();
        }
        setupRecyclerView(mRecyclerViewRecipes);
    }

    private void addRecipeAndIngredientsToDb(final List<Recipe> recipes) {
        database = RecipeDatabase.getInstance(getContext());
        AppExecutor.getInstance().getDbIo().execute(new Runnable() {
            @Override
            public void run() {
                database.ingredientDao().deleteIngredients();
                for(Recipe recipe : recipes){
                    for(Ingredient ingredient : recipe.getIngredients()){
                        ingredient.setRecipeId(recipe.getId());
                        ingredient.setHashId(UUID.randomUUID().toString());
                    }
                    database.recipeDao().setIngredientDao(database.ingredientDao());
                    database.recipeDao().insertRecipeAndIngredients(recipe);
                }
            }
        });
    }

    private void observableFromVM() {
        recipeViewModel.getMutableLiveDataRecipes().observe(this, new Observer<LinkedList<Recipe>>() {
            @Override
            public void onChanged(@Nullable LinkedList<Recipe> recipes) {
                buildRecyclerOrErroView(recipes);
            }
        });
    }

    @SuppressLint("VisibleForTests")
    private void buildRecyclerOrErroView(@Nullable List<Recipe> recipes){
        if(recipes != null){
            buildAdapter(recipes);
            addRecipeAndIngredientsToDb(recipes);
        } else {
            treatNetworkOffline();
        }
    }

    private void buildAdapter(@NonNull List<Recipe> recipes) {
        RecipeAdapter recipeAdapter = new RecipeAdapter(recipes, (RecipeAdapter.OnRecipeListener) getActivity());
        mRecyclerViewRecipes.setAdapter(recipeAdapter);
        mRecyclerViewRecipes.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mProgressLoading.setVisibility(View.INVISIBLE);
    }

    private void treatNetworkOffline() {
        database = RecipeDatabase.getInstance(getContext());
        LiveData<List<Recipe>> recipeLiveData = database.recipeDao().loadRecipes();
        recipeLiveData.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                buildObservableLiveData(recipes);
            }
        });
    }

    @SuppressLint("VisibleForTests")
    private void buildObservableLiveData(List<Recipe> list){
        if(list != null){
            if(list.isEmpty()){
                mErrorMessage.setText(R.string.zero_recipes);
                mErrorMessage.setVisibility(View.VISIBLE);
                mRecyclerViewRecipes.setAdapter(null);
                mRecyclerViewRecipes.setVisibility(View.INVISIBLE);
                mProgressLoading.setVisibility(View.INVISIBLE);
                return;
            }
            buildAdapter(list);
        } else {
            mErrorMessage.setText(R.string.no_network);
            mErrorMessage.setVisibility(View.VISIBLE);
            mRecyclerViewRecipes.setAdapter(null);
            mRecyclerViewRecipes.setVisibility(View.INVISIBLE);
            Snackbar mySnackbar = Snackbar.make(Objects.requireNonNull(this.getView()),R.string.try_again, Snackbar.LENGTH_SHORT);
            mySnackbar.show();
            mProgressLoading.setVisibility(View.INVISIBLE);
        }
        Delayer.processMessage(true, this, ((MainActivity) getActivity()).getIdlingResource());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof RecipeAdapter.OnRecipeListener)) {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDone(boolean finalized) {

    }

}
