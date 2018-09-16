package com.msf.bakingtime.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.msf.bakingtime.BuildConfig;
import com.msf.bakingtime.R;
import com.msf.bakingtime.db.RecipeDatabase;
import com.msf.bakingtime.model.Ingredient;

import java.util.List;

public class RecipeViewFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String KEY_RECIPE = "RECIPE_CHOOSEN";
    private Context mContext;
    private List<Ingredient> mIngredients;
    private RecipeDatabase database;

    public RecipeViewFactory(Context mContext) {
        this.mContext = mContext;
        database = RecipeDatabase.getInstance(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(database == null){
            return;
        }
        SharedPreferences preferences = mContext.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        long recipeId = preferences.getLong(KEY_RECIPE, 0l);
        if(recipeId != 0l){
            mIngredients = database.ingredientDao().loadIngredients(recipeId).getValue();
        }
    }

    @Override
    public void onDestroy() {
        database.close();
        mIngredients = null;
    }

    @Override
    public int getCount() {
        return mIngredients != null ? mIngredients.size():0;
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
        for(Ingredient ingredient : mIngredients){
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
