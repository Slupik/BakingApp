/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipes;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.data.downloader.DatabaseDownloader;
import io.github.slupik.bakingapp.domain.RecipeBean;
import io.github.slupik.bakingapp.view.recipe.RecipeInfoActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    private List<RecipeBean> data;

//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public IntentsTestRule<MainActivity> mIntentRule =
            new IntentsTestRule<>(MainActivity.class);

//    private IdlingResource idlingResource;

    @Before
    public void loadData(){
        new DatabaseDownloader(new DatabaseDownloader.DownloaderCallback() {
            @Override
            public void onDownload(List<RecipeBean> downloaded) {
                data = downloaded;
            }
        }).downloadData();

        //TODO enable idle resources
        //Problem is with multiple test rules

//        Log.d("IDL_RES", idlingResource.getName());
//        idlingResource = mActivityRule.getActivity().getIdlingResource();
//        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void validateIntentSentToPackage() throws InterruptedException {
        int position = 3;

        //Loading time shouldn't be longer
        Thread.sleep(2000);

        onView(withId(R.id.master_list_fragment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        intended(toPackage("io.github.slupik.bakingapp"));
        intended(hasExtra(RecipeInfoActivity.ARG_RECIPE_DATA, new Gson().toJson(data.get(position))));
    }

    @After
    public void unregisterIdlingResource(){
//        if(idlingResource!=null) {
//            IdlingRegistry.getInstance().unregister(idlingResource);
//        }
    }
}