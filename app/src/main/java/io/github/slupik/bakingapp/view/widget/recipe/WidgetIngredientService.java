/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.widget.recipe;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.github.slupik.bakingapp.domain.IngredientBean;

public class WidgetIngredientService extends RemoteViewsService {
    public static final String ARG_INGREDIENTS_LIST = "ingredients-list";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        String data = intent.getStringExtra(ARG_INGREDIENTS_LIST);
        Type collectionType = new TypeToken<ArrayList<IngredientBean>>(){}.getType();
        List<IngredientBean> ingredients = new ArrayList<>();
        try {
            ingredients = new Gson().fromJson(data, collectionType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RecipeWidgetIngredientsListFactory(this.getApplicationContext(), ingredients);
    }
}
