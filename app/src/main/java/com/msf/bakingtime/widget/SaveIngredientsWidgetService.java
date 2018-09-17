package com.msf.bakingtime.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.msf.bakingtime.R;

import static com.msf.bakingtime.widget.RecipeViewFactory.KEY_RECIPE;

public class SaveIngredientsWidgetService extends IntentService{

    public static final String ACTION_UPDATE_TEXT_INGREDIENTS = "com.msf.bakingtime.action.update_ingredients";
    public static final String ACTION_UPDATE_WIDGETS = "com.msf.bakingtime.action.update_widget";


    public SaveIngredientsWidgetService() {
        super(SaveIngredientsWidgetService.class.getSimpleName());
    }

    public static void startActionUpdateTextIngredients(Context context, long recipeId){
        Intent intent = new Intent(context, SaveIngredientsWidgetService.class);
        intent.setAction(ACTION_UPDATE_TEXT_INGREDIENTS);
        intent.putExtra(KEY_RECIPE, recipeId);
        context.startService(intent);
    }

    public static void startActionUpdateWidget(Context context, long recipeId) {
        Intent intent = new Intent(context, SaveIngredientsWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        intent.putExtra(KEY_RECIPE, recipeId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_TEXT_INGREDIENTS.equals(action)) {
                long recipeId = intent.getLongExtra(KEY_RECIPE, 0L);
                handleUpdateText(recipeId);
            } else if (ACTION_UPDATE_WIDGETS.equals(action)) {
                handleUpdateAllWidget();
            }
        }
    }

    private void handleUpdateAllWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.txt_ingredients_widget);
        RecipeWidgetProvider.updateIngredientsWidget(this, appWidgetManager, appWidgetIds);
    }

    private void handleUpdateText(long listIngredients) {
        startActionUpdateWidget(this, listIngredients);
    }


}
