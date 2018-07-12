/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.widget.recipe;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.data.downloader.DatabaseDownloader;
import io.github.slupik.bakingapp.domain.IngredientBean;
import io.github.slupik.bakingapp.domain.RecipeBean;

/**
 * The configuration screen for the {@link RecipeWidget RecipeWidget} AppWidget.
 */
public class RecipeWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "io.github.slupik.bakingapp.view.widget.recipe.RecipeWidget";
    private static final String PREF_PREFIX_NAME_KEY = "appwidget_recipe_name_";
    private static final String PREF_PREFIX_INGREDIENTS_KEY = "appwidget_recipe_ingredients_";

    @BindView(R.id.widget_loading_data)
    ProgressBar loadingIndicator;
    @BindView(R.id.widget_recipe_container)
    LinearLayout container;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public RecipeWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.widget_configure_recipe);
        ButterKnife.bind(this);

        loadingIndicator.setVisibility(View.VISIBLE);
        new DatabaseDownloader(new DatabaseDownloader.DownloaderCallback() {
            @Override
            public void onDownload(List<RecipeBean> data) {
                loadingIndicator.setVisibility(View.GONE);
                for(RecipeBean bean:data) {
                    container.addView(getButton(bean));
                }
            }
        }).downloadData();

        readBundleData();
    }

    private View getButton(final RecipeBean bean) {
        Button btn = new Button(this);
        btn.setText(bean.getName());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = RecipeWidgetConfigureActivity.this;

                // When the button is clicked, store the string locally
                saveRecipeNamePref(context, mAppWidgetId, bean.getName());
                saveIngredientsList(context, mAppWidgetId, bean.getIngredients());

                // It is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RecipeWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

                // Make sure we pass back the original appWidgetId
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return btn;
    }

    private void readBundleData() {
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
    }

/*
        SAVE DATA
 */

/*/
INGREDIENTS
 */
    public static void saveIngredientsList(Context context, int appWidgetId, List<IngredientBean> ingredients) {
        String toSave = new Gson().toJson(ingredients);
        saveWidgetData(context, appWidgetId, PREF_PREFIX_INGREDIENTS_KEY, toSave);
    }
    public static List<IngredientBean> loadIngredientsList(Context context, int appWidgetId) {
        String data = loadWidgetData(context, appWidgetId, PREF_PREFIX_INGREDIENTS_KEY);

        List<IngredientBean> dataToReturn = new ArrayList<>();
        try {
            Type collectionType = new TypeToken<ArrayList<IngredientBean>>(){}.getType();
            dataToReturn = new Gson().fromJson(data, collectionType);
        } catch (JsonSyntaxException ignore) { }
        return dataToReturn;
    }
    public static void deleteIngredientsList(Context context, int appWidgetId) {
        deleteWidgetData(context, appWidgetId, PREF_PREFIX_INGREDIENTS_KEY);
    }
/*
NAME
 */
    // Write the prefix to the SharedPreferences object for this widget
    static void saveRecipeNamePref(Context context, int appWidgetId, String text) {
        saveWidgetData(context, appWidgetId, PREF_PREFIX_NAME_KEY, text);
    }
    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    public static String loadRecipeNamePref(Context context, int appWidgetId) {
        return loadWidgetData(context, appWidgetId, PREF_PREFIX_NAME_KEY);
    }
    public static void deleteRecipeNamePref(Context context, int appWidgetId) {
        deleteWidgetData(context, appWidgetId, PREF_PREFIX_NAME_KEY);
    }

    /*
    BASE
     */
    private static void saveWidgetData(Context context, int appWidgetId, String key, String text){
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(key + appWidgetId, text);
        prefs.apply();
    }
    private static String loadWidgetData(Context context, int appWidgetId, String key){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(key + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }
    private static void deleteWidgetData(Context context, int appWidgetId, String key){
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(key + appWidgetId);
        prefs.apply();
    }
}

