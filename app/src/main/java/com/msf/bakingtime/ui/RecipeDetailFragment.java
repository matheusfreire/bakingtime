package com.msf.bakingtime.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Recipe;

import butterknife.ButterKnife;

import static com.msf.bakingtime.ui.RecipeDetailActivity.RECIPE_KEY;

public class RecipeDetailFragment extends Fragment {
    private Recipe recipe;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RECIPE_KEY)) {
            recipe = getArguments().getParcelable(RECIPE_KEY);
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
}
