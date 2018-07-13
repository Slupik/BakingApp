/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.IngredientBean;

public class IngredientListViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<IngredientBean> values;
    private LayoutInflater mInflater;

    public IngredientListViewAdapter(@NonNull Context context, List<IngredientBean> data) {
        Log.d("INFO_ADAPTER", "size: "+data.size());
        this.mContext = context;
        this.values = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public IngredientBean getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View rowView = mInflater.inflate(R.layout.recipe_ingredient, parent, false);
        ViewHolder holder = new ViewHolder(rowView, getItem(position));

        Log.d("INFO_ADAPTER", "getView");
        return holder.getView();
    }

    class ViewHolder {
        private final View mView;
        private final IngredientBean mBean;

        @BindView(R.id.ingredient_title)
        TextView title;
        @BindView(R.id.ingredient_quantity)
        TextView quantity;
        @BindView(R.id.ingredient_measure)
        TextView measure;

        ViewHolder(View view, IngredientBean bean){
            mView = view;
            mBean = bean;
            ButterKnife.bind(this, view);
            loadData();
        }

        private void loadData() {
            title.setText(mBean.getIngredient());
            quantity.setText(String.valueOf(mBean.getQuantity()));
            measure.setText(mBean.getMeasure());
        }

        View getView(){
            return mView;
        }
    }
}
