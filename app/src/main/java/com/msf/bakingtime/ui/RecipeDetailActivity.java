package com.msf.bakingtime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Recipe;
import com.msf.bakingtime.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements InstructionAdapter.OnInstructionListener {

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mTwoPane = findViewById(R.id.video_container) != null;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            Recipe recipe = getIntent().getParcelableExtra(MainActivity.RECIPE_KEY);
            toolbar.setTitle(recipe.getName());
            arguments.putParcelable(MainActivity.RECIPE_KEY, recipe);
            arguments.putParcelableArrayList(MainActivity.INGREDIENTS_KEY, getIntent().getParcelableArrayListExtra(MainActivity.INGREDIENTS_KEY));
            arguments.putParcelableArrayList(MainActivity.STEPS_KEY, getIntent().getParcelableArrayListExtra(MainActivity.STEPS_KEY));
            RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(this,arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        } else {
            navigateUpTo(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onItemClick(Step step) {
        VideoFragment videoFragment = VideoFragment.newInstance(step, null);
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.recipe_detail_container, videoFragment).commit();
    }
}
