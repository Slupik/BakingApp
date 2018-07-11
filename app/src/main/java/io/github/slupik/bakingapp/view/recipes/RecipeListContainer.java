/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipes;

import java.util.List;

import io.github.slupik.bakingapp.domain.RecipeBean;

public class RecipeListContainer {
    public final List<RecipeBean> RECIPES;

    public RecipeListContainer(List<RecipeBean> recipes) {
        RECIPES = recipes;
    }

    public int getCount(){
        return RECIPES.size();
    }
}
