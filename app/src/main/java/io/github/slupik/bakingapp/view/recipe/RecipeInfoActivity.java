/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipe;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.RecipeBean;
import io.github.slupik.bakingapp.domain.StepBean;

public class RecipeInfoActivity extends AppCompatActivity implements RecipeInfoFragment.RecipeInfoFragmentInteractionInterface {
    public static final String ARG_RECIPE_DATA = "recipe-data";
    private RecipeBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        Bundle bundledData = getIntent().getExtras();
        if(bundledData!=null) {
            String savedData = bundledData.getString(ARG_RECIPE_DATA, "");
            data = new Gson().fromJson(savedData, RecipeBean.class);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeInfoFragment fragment = (RecipeInfoFragment)fragmentManager.findFragmentById(R.id.master_list_fragment);
        fragment.setNewData(data);
    }

    @Override
    public RecipeBean getData() {
        return data;
    }

    @Override
    public void openActivityForStep(StepBean item) {
        //TODO fill this
    }
}
