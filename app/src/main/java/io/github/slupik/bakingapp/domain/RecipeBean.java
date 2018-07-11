/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeBean {
    @SerializedName("id")
    private int id = -1;
    @SerializedName("name")
    private String name = "";
    @SerializedName("image")
    private String img = "";
    @SerializedName("servings")
    private int servings = -1;
    @SerializedName("ingredients")
    private List<IngredientBean> ingredients = new ArrayList<>();
    @SerializedName("steps")
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
