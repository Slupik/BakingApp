/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.RecipeBean;

/**
 * {@link RecyclerView.Adapter} that can display a {@link RecipeBean} and makes a call to the
 * specified {@link RecipesFragment.RecipeListFragmentInterface}.
 */
public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder> {

    private final List<RecipeBean> mValues;
    private final RecipesFragment.RecipeListFragmentInterface mListener;

    public RecipesRecyclerViewAdapter(List<RecipeBean> items, RecipesFragment.RecipeListFragmentInterface listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_element_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.loadData(mValues.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        @BindView(R.id.recipe_with_image_container_on_list) LinearLayout mImageContainer;
        @BindView(R.id.recipe_image_on_list) ImageView mImage;
        @BindView(R.id.recipe_name_on_list) TextView mName;
        @BindView(R.id.recipe_placeholder_on_list) TextView mPlaceholder;
        private RecipeBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            mImageContainer.setVisibility(View.GONE);
            mPlaceholder.setVisibility(View.VISIBLE);
        }

        public void loadData(RecipeBean data) {
            mItem = data;
            mImage.setImageDrawable(null);
            loadImage(data.getImg());
            mName.setText(data.getName());
            mPlaceholder.setText(data.getName());
        }

        public RecipeBean getData(RecipeBean data) {
            return data;
        }

        private void loadImage(String link) {
            if(link==null || link.length() == 0) return;
            Picasso
                    .with(mView.getContext())
                    .load(link)
                    .into(mImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            mImageContainer.setVisibility(View.VISIBLE);
                            mPlaceholder.setVisibility(View.GONE);
                        }
                        @Override
                        public void onError() {

                        }
                    });
        }

        @Override
        public String toString() {
            return new Gson().toJson(mItem);
        }
    }
}
