package com.msf.bakingtime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeListener {

    public static final String RECIPE_KEY = "recipe";
    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String STEPS_KEY = "steps";
    private boolean mTwoPane;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle(getString(R.string.app_name));
        mTwoPane = findViewById(R.id.recipe_detail_container) != null;
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Bundle b = new Bundle();
        b.putParcelable(RECIPE_KEY, recipe);
        b.putParcelableArrayList(INGREDIENTS_KEY, (ArrayList<? extends Parcelable>) recipe.getIngredients());
        b.putParcelableArrayList(STEPS_KEY, (ArrayList<? extends Parcelable>) recipe.getSteps());
        final Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
