/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.google.gson.Gson;

import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.RecipeBean;
import io.github.slupik.bakingapp.view.recipe.RecipeInfoActivity;

public class MainActivity extends AppCompatActivity implements RecipesFragment.RecipeListFragmentInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupColumnCount();
    }

    private void setupColumnCount() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int columnCount;
        int divider = 220;
        columnCount = (int)Math.floor(dpWidth/divider);
        columnCount--;
        columnCount = Math.max(1, columnCount);

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipesFragment fragment = (RecipesFragment)fragmentManager.findFragmentById(R.id.master_list_fragment);
        fragment.setColumnCount(columnCount);
    }

    @Override
    public void onListFragmentInteraction(RecipeBean item) {
        Intent intent = new Intent(this, RecipeInfoActivity.class);
        intent.putExtra(RecipeInfoActivity.ARG_RECIPE_DATA, new Gson().toJson(item));
        startActivity(intent);
    }
}
