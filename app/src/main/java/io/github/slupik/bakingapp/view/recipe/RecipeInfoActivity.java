/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.RecipeBean;
import io.github.slupik.bakingapp.domain.StepBean;
import io.github.slupik.bakingapp.view.details.DetailsActivity;
import io.github.slupik.bakingapp.view.details.StepFragment;

public class RecipeInfoActivity extends AppCompatActivity implements RecipeInfoFragment.RecipeInfoFragmentInteractionInterface, StepFragment.OnFragmentInteractionListener {
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
    public void openInfoForStep(List<StepBean> steps, int stepId) {
        StepFragment fragment = getStepFragment();
        if(fragment==null) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.ARG_STEP_LIST, new Gson().toJson(steps));
            intent.putExtra(DetailsActivity.ARG_STEP_NUMBER, stepId);
            startActivity(intent);
        } else {
            fragment.setStepData(steps.get(stepId));
        }
    }

    private StepFragment getStepFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        return (StepFragment)fragmentManager.findFragmentById(R.id.step_fragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
