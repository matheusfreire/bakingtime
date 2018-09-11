package com.msf.bakingtime.ui;

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

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Ingredient;
import com.msf.bakingtime.model.Recipe;
import com.msf.bakingtime.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.msf.bakingtime.ui.MainActivity.INGREDIENTS_KEY;
import static com.msf.bakingtime.ui.MainActivity.RECIPE_KEY;
import static com.msf.bakingtime.ui.MainActivity.STEPS_KEY;

public class RecipeDetailFragment extends Fragment {
    private Recipe recipe;

    @BindView(R.id.list_ingredients)
    ListView mListIngredients;

    @BindView(R.id.recyclerViewInstructions)
    RecyclerView mRecyclerInstructions;

    private InstructionAdapter.OnInstructionListener mListenerVideo;

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
        setupRecyclerView();
        if(recipe != null){
            InstructionAdapter instructionAdapter = new InstructionAdapter(recipe.getSteps(), mListenerVideo);
            mRecyclerInstructions.setAdapter(instructionAdapter);
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerInstructions.setLayoutManager(linearLayoutManager);
        mRecyclerInstructions.setItemAnimator(new DefaultItemAnimator());
        mRecyclerInstructions.setHasFixedSize(true);
    }

    private void buildListIngredients() {
        if(recipe != null){
            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(),
                    R.layout.ingredient,recipe.getIngredients());
            mListIngredients.setAdapter(ingredientsAdapter);
        }
    }

}
