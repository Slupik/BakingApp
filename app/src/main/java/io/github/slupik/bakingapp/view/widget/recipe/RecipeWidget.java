/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.widget.recipe;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import java.util.List;

import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.IngredientBean;

import static io.github.slupik.bakingapp.view.widget.recipe.WidgetIngredientService.ARG_INGREDIENTS_LIST;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link RecipeWidgetConfigureActivity RecipeWidgetConfigureActivity}
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence recipeName = RecipeWidgetConfigureActivity.loadRecipeNamePref(context, appWidgetId);
        List<IngredientBean> ingredients = RecipeWidgetConfigureActivity.loadIngredientsList(context, appWidgetId);
        String ingredientsInString = new Gson().toJson(ingredients);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
        views.setTextViewText(R.id.widget_recipe_name, recipeName);

        Intent intent = new Intent(context, WidgetIngredientService.class);
        intent.putExtra(ARG_INGREDIENTS_LIST, ingredientsInString);
        views.setRemoteAdapter(R.id.widget_recipe_list, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            RecipeWidgetConfigureActivity.deleteRecipeNamePref(context, appWidgetId);
            RecipeWidgetConfigureActivity.deleteIngredientsList(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

