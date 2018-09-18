package com.msf.bakingtime.widget;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.msf.bakingtime.BuildConfig;
import com.msf.bakingtime.R;
import com.msf.bakingtime.db.RecipeDatabase;
import com.msf.bakingtime.model.Ingredient;

import java.util.List;

public class RecipeWidgetViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeViewFactory(this.getApplicationContext());
    }
}

class RecipeViewFactory implements RemoteViewsService.RemoteViewsFactory, LifecycleOwner {

    private Context mContext;
    private List<Ingredient> mIngredients;
    private RecipeDatabase database;
    private LifecycleRegistry mLifecycleRegistry;

    private void getFromDb(){
        SharedPreferences preferences = mContext.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        long recipeId = preferences.getLong(SaveIngredientsWidgetService.KEY_RECIPE, 0L);
        Log.d(RecipeViewFactory.class.getSimpleName(), String.valueOf(recipeId));
        if (recipeId != 0L) {
            database.ingredientDao().loadIngredients(recipeId).observe(this, new Observer<List<Ingredient>>() {
                @Override
                public void onChanged(@Nullable List<Ingredient> ingredientList) {
                    mIngredients = ingredientList;
                }
            });
        }
    }

    RecipeViewFactory(Context mContext) {
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        this.mContext = mContext;
        database = RecipeDatabase.getInstance(mContext);
        getFromDb();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (database == null) {
            return;
        }

    }

    @Override
    public void onDestroy() {
        database.close();
        mIngredients = null;
    }

    @Override
    public int getCount() {
        return mIngredients != null ? mIngredients.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients == null) {
            return null;
        }

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.recipe_message, buildIngredients());
        return null;
    }

    private String buildIngredients() {
        StringBuilder sb = new StringBuilder();
        for (Ingredient ingredient : mIngredients) {
            sb.append(ingredient.getIngredient()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
