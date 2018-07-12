/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.widget.recipe;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.IngredientBean;

public class RecipeWidgetIngredientsListFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final List<IngredientBean> mIngredients = new ArrayList<>();

    public RecipeWidgetIngredientsListFactory(Context context, List<IngredientBean> data) {
        mContext = context;
        mIngredients.clear();
        mIngredients.addAll(data);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients == null || position>=mIngredients.size()) return null;
        return getIngredientView(mIngredients.get(position));
    }

    private RemoteViews getIngredientView(IngredientBean bean) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_ingredient);
        views.setTextViewText(R.id.widget_ingredient_title, bean.getIngredient());
        views.setTextViewText(R.id.widget_ingredient_quantity, String.valueOf(bean.getQuantity()));
        views.setTextViewText(R.id.widget_ingredient_measure, bean.getMeasure());
        return views;
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
