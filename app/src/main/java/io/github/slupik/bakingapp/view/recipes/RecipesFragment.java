/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.recipes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.data.downloader.DatabaseDownloader;
import io.github.slupik.bakingapp.domain.RecipeBean;
import io.github.slupik.bakingapp.view.recipes.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link RecipeListFragmentInterface}
 * interface.
 */
public class RecipesFragment extends Fragment {
    private static final boolean TEST_UX = false;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private RecipeListFragmentInterface mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipesFragment() {
    }

    @SuppressWarnings("unused")
    public static RecipesFragment newInstance(int columnCount) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            final RecipesRecyclerViewAdapter adapter = new RecipesRecyclerViewAdapter(getStarterData(), mListener);
            recyclerView.setAdapter(adapter);

            if(!TEST_UX) {
                new DatabaseDownloader(new DatabaseDownloader.DownloaderCallback() {
                    @Override
                    public void onDownload(List<RecipeBean> data) {
                        adapter.setNewData(data);
                    }
                }).downloadData();
            }

        }
        return view;
    }

    private List<RecipeBean> getStarterData() {
        if(TEST_UX) {
            return DummyContent.ITEMS;
        }
        return new ArrayList<>();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeListFragmentInterface) {
            mListener = (RecipeListFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RecipeListFragmentInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface RecipeListFragmentInterface {
        void onListFragmentInteraction(RecipeBean item);
    }
}
