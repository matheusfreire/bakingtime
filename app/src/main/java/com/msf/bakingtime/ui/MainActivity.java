package com.msf.bakingtime.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Recipe;


public class MainActivity extends AppCompatActivity implements ListRecipesFragment.OnRecipeListener{

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTwoPane = findViewById(R.id.recipe_detail_container) != null;
    }

    @Override
    public void onItemClick(Recipe recipe) {

    }
}
