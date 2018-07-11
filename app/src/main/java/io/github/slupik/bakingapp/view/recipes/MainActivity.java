/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.RecipesFragment;
import io.github.slupik.bakingapp.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements RecipesFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
