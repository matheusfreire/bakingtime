package com.msf.bakingtime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Recipe;
import com.msf.bakingtime.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.msf.bakingtime.ui.MainActivity.RECIPE_KEY;

public class RecipeDetailActivity extends AppCompatActivity implements InstructionAdapter.OnInstructionListener {

    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;

    private Recipe recipe;

    private boolean mTwoPane;

    @BindView(R.id.linear_layout_tablet)
    LinearLayout linearLayoutTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipe = getIntent().getParcelableExtra(RECIPE_KEY);
        Bundle arguments = new Bundle();
        arguments.putParcelable(RECIPE_KEY, recipe);
        arguments.putParcelableArrayList(MainActivity.INGREDIENTS_KEY, getIntent().getParcelableArrayListExtra(MainActivity.INGREDIENTS_KEY));
        arguments.putParcelableArrayList(MainActivity.STEPS_KEY, getIntent().getParcelableArrayListExtra(MainActivity.STEPS_KEY));
        RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(this,arguments);

        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mTwoPane = findViewById(R.id.video_container) != null;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setTitle(recipe.getName());
        if(linearLayoutTablet == null) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.recipe_detail_container, fragment).commit();
            }
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_container, fragment).commit();
            mTwoPane = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
            toolbar.setTitle(recipe.getName());
        } else {
            navigateUpTo(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onItemClick(Step step, Step nextStep) {
        VideoFragment videoFragment = VideoFragment.newInstance(step, nextStep);
        if(mTwoPane){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_container, videoFragment)
                    .commit();
        } else {
            toolbar.setTitle(step.getShortDescription());
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.recipe_detail_container, videoFragment).commit();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipe = savedInstanceState.getParcelable(RECIPE_KEY);
        toolbar.setTitle(recipe.getName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_KEY, recipe);
    }
}
