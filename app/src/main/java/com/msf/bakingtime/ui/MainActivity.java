package com.msf.bakingtime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeListener {

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
        b.putParcelable("recipe", recipe);
        final Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
