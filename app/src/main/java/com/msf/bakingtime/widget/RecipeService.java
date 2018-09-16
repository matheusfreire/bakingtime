package com.msf.bakingtime.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeViewFactory(this.getApplicationContext());
    }
}
