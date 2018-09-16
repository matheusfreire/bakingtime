package com.msf.bakingtime.ui;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.msf.bakingtime.R;
import com.msf.bakingtime.db.RecipeDatabase;
import com.msf.bakingtime.model.Ingredient;
import com.msf.bakingtime.model.Recipe;
import com.msf.bakingtime.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.msf.bakingtime.ui.MainActivity.INGREDIENTS_KEY;
import static com.msf.bakingtime.ui.MainActivity.RECIPE_KEY;
import static com.msf.bakingtime.ui.MainActivity.STEPS_KEY;

public class RecipeDetailFragment extends FragmentsRecipe {
    private Recipe recipe;

    @BindView(R.id.list_ingredients)
    ListView mListIngredients;

    @BindView(R.id.recyclerViewInstructions)
    RecyclerView mRecyclerInstructions;

    @BindView(R.id.no_network_detected)
    TextView mTextViewNoNetwork;

    private InstructionAdapter.OnInstructionListener mListenerVideo;

    private RecipeDatabase database;

    public RecipeDetailFragment() {
    }

    public static RecipeDetailFragment newInstance(InstructionAdapter.OnInstructionListener listener, Bundle arguments) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.mListenerVideo = listener;
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(RECIPE_KEY)) {
            recipe = getArguments().getParcelable(RECIPE_KEY);
            recipe.setIngredients(getArguments().<Ingredient>getParcelableArrayList(INGREDIENTS_KEY));
            recipe.setSteps(getArguments().<Step>getParcelableArrayList(STEPS_KEY));
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildListIngredients();
        buildRecyclerInstruction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!(context instanceof InstructionAdapter.OnInstructionListener)){
            throw new RuntimeException(context.toString() + "must implement InstructionAdapter.OnInstructionListener");
        }
    }

    private void buildRecyclerInstruction() {
        setupRecyclerView(mRecyclerInstructions);
        if(recipe != null && isOnline()){
            InstructionAdapter instructionAdapter = new InstructionAdapter(recipe.getSteps(), mListenerVideo);
            mRecyclerInstructions.setAdapter(instructionAdapter);
        } else if(!isOnline()){
            mTextViewNoNetwork.setVisibility(View.VISIBLE);
        }
    }

    private void buildListIngredients() {
        if(recipe != null){
            if(isOnline()){
                buildIngredientsAdapter(recipe.getIngredients());
            } else {
                database = RecipeDatabase.getInstance(getContext());
                database.ingredientDao().loadIngredients(recipe.getId()).observe(this, new Observer<List<Ingredient>>() {
                    @Override
                    public void onChanged(@Nullable List<Ingredient> ingredientList) {
                        buildIngredientsAdapter(ingredientList);
                    }
                });
            }
        }
    }

    private void buildIngredientsAdapter(List<Ingredient> ingredientList) {
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(),
                R.layout.ingredient,ingredientList);
        mListIngredients.setAdapter(ingredientsAdapter);
    }

}
