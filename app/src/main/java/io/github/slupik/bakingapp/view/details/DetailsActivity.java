/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.IngredientBean;
import io.github.slupik.bakingapp.domain.StepBean;

public class DetailsActivity extends AppCompatActivity implements StepFragment.OnFragmentInteractionListener {

    public static final String ARG_STEP_LIST = "step_list";
    public static final String ARG_STEP_NUMBER = "step_number";
    public static final String ARG_INGREDIENT_LIST = "ingredient_list";

    private List<IngredientBean> ingredientsData = new ArrayList<>();
    private List<StepBean> stepsData = new ArrayList<>();
    private int actualStepIndex = 0;
    private StepFragment stepFragment;

    @BindView(R.id.previous_step_btn)
    public Button btnPrevious;
    @BindView(R.id.next_step_btn)
    public Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        hideActionBar();
        ButterKnife.bind(this);
        setupFragmentVariable();
        readExtraData();
        adjustAccessibilityOfButtons();
    }

    private void hideActionBar() {
        ActionBar bar = getSupportActionBar();
        if(bar!=null) {
            bar.hide();
        }
    }

    private void readExtraData() {
        Intent intent = getIntent();
        if(intent!=null) {
            String stepList = intent.getStringExtra(ARG_STEP_LIST);
            if(stepList!=null){
                Type collectionType = new TypeToken<ArrayList<StepBean>>(){}.getType();
                stepsData = new Gson().fromJson(stepList, collectionType);

                actualStepIndex = intent.getIntExtra(ARG_STEP_NUMBER, 0);
                stepFragment.setStepData(getActualStep());
            } else {
                String ingredientList = intent.getStringExtra(ARG_INGREDIENT_LIST);
                Type collectionType = new TypeToken<ArrayList<IngredientBean>>(){}.getType();
                ingredientsData = new Gson().fromJson(ingredientList, collectionType);

                stepFragment.setIngredientsData(ingredientsData);
            }
        }
    }

    private void setupFragmentVariable() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        stepFragment = (StepFragment)fragmentManager.findFragmentById(R.id.step_fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private StepBean getActualStep() {
        if(stepsData !=null && stepsData.size()>actualStepIndex) {
            return stepsData.get(actualStepIndex);
        } else {
            return null;
        }
    }

    @OnClick(R.id.next_step_btn)
    public void nextStep(){
        actualStepIndex++;
        adjustAccessibilityOfButtons();
        stepFragment.setStepData(getActualStep());
    }

    @OnClick(R.id.previous_step_btn)
    public void previousStep(){
        actualStepIndex--;
        adjustAccessibilityOfButtons();
        stepFragment.setStepData(getActualStep());
    }

    private void adjustAccessibilityOfButtons(){
        if(actualStepIndex==0) {
            setButtonEnabled(btnPrevious, false);
        } else {
            setButtonEnabled(btnPrevious, true);
        }
        if(actualStepIndex>= stepsData.size()-1) {
            setButtonEnabled(btnNext, false);
        } else {
            setButtonEnabled(btnNext, true);
        }
    }

    private void setButtonEnabled(Button button, boolean enable) {
        button.setEnabled(enable);
        if(enable) {
            button.setTextColor(getResources().getColor(R.color.darkDimgray));
        } else {
            button.setTextColor(getResources().getColor(R.color.dimgray));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_STEP_LIST, new Gson().toJson(stepsData));
        outState.putInt(ARG_STEP_NUMBER, actualStepIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String stepList = savedInstanceState.getString(ARG_STEP_LIST);
        Type collectionType = new TypeToken<ArrayList<StepBean>>(){}.getType();
        stepsData = new Gson().fromJson(stepList, collectionType);

        actualStepIndex = savedInstanceState.getInt(ARG_STEP_NUMBER, 0);
        stepFragment.setStepData(getActualStep());
        adjustAccessibilityOfButtons();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
