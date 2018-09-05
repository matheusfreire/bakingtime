package com.msf.bakingtime.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
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

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RECIPE_KEY)) {
            recipe = getArguments().getParcelable(RECIPE_KEY);
            recipe.setIngredients(getArguments().<Ingredient>getParcelableArrayList(INGREDIENTS_KEY));
            recipe.setSteps(getArguments().<Step>getParcelableArrayList(STEPS_KEY));
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(recipe.getName());
            }
        }
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
    }

    private void buildListIngredients() {
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(),
                R.layout.ingredient,recipe.getIngredients());
        mListIngredients.setAdapter(ingredientsAdapter);
    }
}
