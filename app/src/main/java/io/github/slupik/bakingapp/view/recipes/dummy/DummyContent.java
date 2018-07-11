/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipes.dummy;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.slupik.bakingapp.Randomizer;
import io.github.slupik.bakingapp.domain.RecipeBean;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<RecipeBean> ITEMS = new ArrayList<RecipeBean>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, RecipeBean> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(RecipeBean item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static RecipeBean createDummyItem(int position) {
        RecipeBean dummy = new RecipeBean();
        dummy.setName(Randomizer.randomStandardString(10));
        dummy.setId(position);
        if(Randomizer.randomInteger(0, 1)==0){
            Log.d("DUMMY ITEM", "Set img!!! for id "+dummy.getId());
            dummy.setImg("https://cdn.cnn.com/cnnnext/dam/assets/171027052520-processed-foods-exlarge-tease.jpg");
        } else {
            dummy.setImg("");
        }
        return dummy;
    }
}
