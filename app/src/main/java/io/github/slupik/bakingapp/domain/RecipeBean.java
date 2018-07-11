/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.domain;

import java.util.ArrayList;
import java.util.List;

public class RecipeBean {
    private int id = -1;
    private String name = "";
    private String img = "";
    private int servings = -1;
    private List<IngredientBean> ingredients = new ArrayList<>();
    private List<StepBean> steps = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<IngredientBean> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientBean> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepBean> getSteps() {
        return steps;
    }

    public void setSteps(List<StepBean> steps) {
        this.steps = steps;
    }
}
