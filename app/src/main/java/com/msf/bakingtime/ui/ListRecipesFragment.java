package com.msf.bakingtime.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Recipe;
import com.msf.bakingtime.network.RecipeEndPoint;
import com.msf.bakingtime.network.RetrofitClientInstance;
import com.msf.bakingtime.viewmodel.RecipeListViewModelFactory;
import com.msf.bakingtime.viewmodel.RecipeViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListRecipesFragment extends Fragment {

    private OnRecipeListener mListener;

    @BindView(R.id.recipe_list)
    RecyclerView mRecyclerViewRecipes;


    @BindView(R.id.progress_loading)
    ProgressBar mProgressLoading;

    @BindView(R.id.error_message)
    TextView mErrorMessage;

    private RecipeViewModel recipeViewModel;

    public ListRecipesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_recipes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecipeEndPoint endPoint = RetrofitClientInstance.getRetrofitInstance().create(RecipeEndPoint.class);
        RecipeListViewModelFactory factory = new RecipeListViewModelFactory(endPoint.callRecipes(), getActivity().getApplication());
        recipeViewModel = ViewModelProviders.of(this, factory).get(RecipeViewModel.class);
        mProgressLoading.setVisibility(View.VISIBLE);
        mRecyclerViewRecipes.setVisibility(View.INVISIBLE);
        if(isOnline()){
            observableFromVM();
        } else {
            showNetworkOffline();
        }
        buildRecycler();
    }

    private void observableFromVM() {
        recipeViewModel.getMutableLiveDataRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.d("Teste", String.valueOf(recipes.size()));
            }
        });
    }

    private void showNetworkOffline() {
        mErrorMessage.setText(R.string.no_network);
        mRecyclerViewRecipes.setAdapter(null);
        mRecyclerViewRecipes.setVisibility(View.INVISIBLE);
        Snackbar mySnackbar = Snackbar.make(getActivity().getCurrentFocus(),R.string.try_again, Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    public void onButtonPressed(Recipe recipe) {
        if (mListener != null) {
            mListener.onItemClick(recipe);
        }
    }

    private void buildRecycler() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewRecipes.setLayoutManager(mLayoutManager);
        mRecyclerViewRecipes.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewRecipes.setHasFixedSize(true);
    }

    private boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeListener) {
            mListener = (OnRecipeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRecipeListener {
        void onItemClick(Recipe recipe);
    }
}
