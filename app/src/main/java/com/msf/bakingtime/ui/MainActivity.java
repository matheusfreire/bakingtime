package com.msf.bakingtime.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements ListRecipesFragment.OnRecipeListener{

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

    }
}
