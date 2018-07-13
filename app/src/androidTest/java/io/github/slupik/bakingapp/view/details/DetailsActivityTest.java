/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.details;

import android.content.Intent;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.RecipeStaticObject;
import io.github.slupik.bakingapp.domain.RecipeBean;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DetailsActivityTest {
    private RecipeBean mRecipe;
    private final int STEP_INDEX = 2;

    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<DetailsActivity> mActivityRule =
            new ActivityTestRule<>(DetailsActivity.class);

    @Before
    public void setUp() {
        mRecipe = RecipeStaticObject.getBean();
        final Intent i = new Intent();
        i.putExtra(DetailsActivity.ARG_STEP_LIST, new Gson().toJson(mRecipe.getSteps()));
        i.putExtra(DetailsActivity.ARG_STEP_NUMBER, STEP_INDEX);
        mActivityRule.launchActivity(i);
    }

    @Test
    public void clickOnStep_checkStepDetails(){
//        onView(withId(R.id.frag_recipe_info_list))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(stepIndex+1, click()));

        onView(withId(R.id.step_full_description_tv)).
                check(matches(withText(mRecipe.getSteps().get(STEP_INDEX).getDescription())));
    }
}