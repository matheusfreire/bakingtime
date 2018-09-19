package com.msf.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.msf.bakingtime.R;

public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_view);
        Intent intentToViewService = new Intent(context, RecipeWidgetViewService.class);
        intentToViewService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        remoteViews.setRemoteAdapter(R.id.list_view_ingredients, intentToViewService);
        remoteViews.setEmptyView(R.id.list_view_ingredients, R.id.txt_pick_recipe);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SaveIngredientsWidgetService.startActionUpdateWidget(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateIngredientsWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWid getManager, appWidgetId);
        }
    }
}

