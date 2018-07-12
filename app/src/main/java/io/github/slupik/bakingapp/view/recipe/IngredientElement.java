/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipe;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.IngredientBean;
import io.github.slupik.bakingapp.view.ViewHolder;

public class IngredientElement extends ViewHolder {
    private Context mContext;
    public final View VIEW;

    @BindView(R.id.ingredient_title)
    TextView title;
    @BindView(R.id.ingredient_quantity)
    TextView quantity;
    @BindView(R.id.ingredient_measure)
    TextView measure;

    public IngredientElement(Context context) {
        this(R.layout.recipe_ingredient, context);
    }

    public IngredientElement(@LayoutRes int layoutID, Context context) {
        View view = getInflatedView(layoutID, context);
        ButterKnife.bind(this, view);
        VIEW = view;
        mContext = view.getContext();
    }

    @SuppressWarnings("deprecation")
    public View populate(IngredientBean data) {
        title.setText(data.getIngredient());
        measure.setText(data.getMeasure());

        String quantityText;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            quantityText = String.format(getContext().getResources().getConfiguration().getLocales().get(0), "%1$,.2f", data.getQuantity());
        } else {
            quantityText = String.format(getContext().getResources().getConfiguration().locale, "%1$,.2f", data.getQuantity());
        }
        quantity.setText(quantityText);

        return VIEW;
    }

    @Override
    protected Context getContext() {
        return mContext;
    }
}
