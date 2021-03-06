/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.RecipeBean;
import io.github.slupik.bakingapp.domain.StepBean;
import io.github.slupik.bakingapp.view.recipe.RecipeInfoFragment.RecipeInfoFragmentInteractionInterface;

/**
 * {@link RecyclerView.Adapter} that can display a {@link RecipeBean} and makes a call to the
 * specified {@link RecipeInfoFragmentInteractionInterface}.
 */
public class RecipeInfoRecyclerViewAdapter extends RecyclerView.Adapter<RecipeInfoRecyclerViewAdapter.ViewHolder> {

    private static final int VIEW_TYPE_INGREDIENTS = 0;
    private static final int VIEW_TYPE_STEP = 1;
    private RecipeBean data = new RecipeBean();
    private final RecipeInfoFragment.RecipeInfoFragmentInteractionInterface mListener;

    RecipeInfoRecyclerViewAdapter(RecipeInfoFragmentInteractionInterface listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case VIEW_TYPE_INGREDIENTS:
                layout = R.layout.recipe_ingredient_list;
                break;
            case VIEW_TYPE_STEP:
                layout = R.layout.recipe_step;
                break;
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case VIEW_TYPE_INGREDIENTS:
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            mListener.openInfoForIngredients(data.getIngredients());
                        }
                    }
                });
                break;
            case VIEW_TYPE_STEP:
                final StepBean bean = getAppropriateStep(position);
                holder.loadData(bean);

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mListener) {
                            mListener.openInfoForStep(data.getSteps(), bean.getId());
                        }
                    }
                });
                break;
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }
    }

    private StepBean getAppropriateStep(int position) {
        return data.getSteps().get(position-1);
    }

    @Override
    public int getItemCount() {
        if(data==null) return 0;
        return data.getSteps().size()+1;//1 - this is how many space takes ingredients
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_INGREDIENTS;
        } else {
            return VIEW_TYPE_STEP;
        }
    }

    public void setNewData(RecipeBean data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;

        @BindView(R.id.ingredients_container)
        @Nullable
        ListView ingredientsContainer;

        @BindView(R.id.recipe_step_desc)
        @Nullable
        TextView stepDesc;

        @BindView(R.id.recipe_step_image)
        @Nullable
        ImageView imageOfStep;

        @BindView(R.id.recipe_step_full_desc)
        @Nullable
        TextView fullStepDesc;

        private StepBean step;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        public void loadData(StepBean data){
            step = data;
            if (stepDesc != null) {
                stepDesc.setText(data.getShortDescription());
            }
            if(fullStepDesc!=null) {
                fullStepDesc.setText(data.getDescription());
            }
            if (imageOfStep != null && data.getFixedThumbnailURL().length()>0) {
                imageOfStep.setVisibility(View.VISIBLE);
                Picasso.with(imageOfStep.getContext()).load(data.getFixedThumbnailURL()).into(imageOfStep);
            } else {
                if(imageOfStep!=null) {
                    imageOfStep.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public String toString() {
            if(step!=null) {
                return new Gson().toJson(step);
            }
            return "Empty ViewHolder with Recipe Info";
        }
    }
}
