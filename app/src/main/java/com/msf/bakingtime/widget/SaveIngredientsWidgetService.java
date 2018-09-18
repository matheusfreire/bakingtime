package com.msf.bakingtime.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.msf.bakingtime.BuildConfig;
import com.msf.bakingtime.R;

public class SaveIngredientsWidgetService extends IntentService{
    public static final String KEY_RECIPE = "RECIPE_CHOOSEN";

    public static final String ACTION_UPDATE_TEXT_INGREDIENTS = "com.msf.bakingtime.action.update_ingredients";
    public static final String ACTION_UPDATE_WIDGET = "com.msf.bakingtime.action.update_widget";


    public SaveIngredientsWidgetService() {
        super(SaveIngredientsWidgetService.class.getSimpleName());
    }

    public static void startActionUpdateTextIngredients(Context context, long recipeId){
        Intent intent = new Intent(context, SaveIngredientsWidgetService.class);
        intent.setAction(ACTION_UPDATE_TEXT_INGREDIENTS);
        intent.putExtra(KEY_RECIPE, recipeId);
        context.startService(intent);
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, SaveIngredientsWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_TEXT_INGREDIENTS.equals(action)) {
                long recipeId = intent.getLongExtra(KEY_RECIPE, 0L);
                handleUpdateText(recipeId);
            } else if (ACTION_UPDATE_WIDGET.equals(action)) {
                handleUpdateWidget();
            }
        }
    }

    private void handleUpdateText(long recipeId) {
        saveRecipeIdOnSharedPreferences(recipeId);
        startActionUpdateWidget(this);
    }

    private void handleUpdateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view_ingredients);
        RecipeWidgetProvider.updateIngredientsWidget(this, appWidgetManager, appWidgetIds);
    }

    private void saveRecipeIdOnSharedPreferences(long recipeId) {
        SharedPreferences sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_RECIPE, recipeId);
        editor.apply();
    }


}
