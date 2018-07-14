/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.view.details;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.slupik.bakingapp.R;
import io.github.slupik.bakingapp.domain.IngredientBean;
import io.github.slupik.bakingapp.domain.StepBean;
import io.github.slupik.bakingapp.view.recipe.IngredientListViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFragment extends Fragment {

    @BindView(R.id.ingredients_container)
    ListView ingredientsList;

    @BindView(R.id.step_info_container)
    ScrollView stepInfoContainer;
    @BindView(R.id.step_full_description_tv)
    public TextView fullDesc;
    @BindView(R.id.video_space)
    public SimpleExoPlayerView videoView;
    private SimpleExoPlayer exoPlayer;
    private long lastTime = 0;
    private long lastId = -1;
    private String lastVideoURL = "";

    private static final String ARG_STEP_DATA = "step-data";
    private static final String ARG_VIDEO_TIME = "video-time";
    private static final String ARG_ID_OF_STEP_ON_VIDEO = "video-step-id";

    private StepBean actualStep;
    private OnFragmentInteractionListener mListener;

    public StepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param step Step which will be shown.
     * @return A new instance of fragment StepFragment.
     */
    public static StepFragment newInstance(StepBean step) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STEP_DATA, new Gson().toJson(step));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setStepData(new Gson().fromJson(getArguments().getString(ARG_STEP_DATA), StepBean.class));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setIngredientsData(List<IngredientBean> data) {
        actualStep = null;
        stepInfoContainer.setVisibility(View.GONE);
        ingredientsList.setVisibility(View.VISIBLE);
        if(ingredientsList!=null && data!=null) {
            ingredientsList.setAdapter(new IngredientListViewAdapter(ingredientsList.getContext(), data));
        }
    }

    public void setStepData(@Nullable StepBean actualStep) {
        this.actualStep = actualStep;
        ingredientsList.setVisibility(View.GONE);
        stepInfoContainer.setVisibility(View.VISIBLE);
        if(exoPlayer!=null) {
            exoPlayer.stop();
        }
        if (actualStep != null) {
            fullDesc.setText(actualStep.getDescription());
            String filmURL = actualStep.getFixedVideoURL();
            if(filmURL.length()>0) {
                videoView.setVisibility(View.VISIBLE);
                loadVideoURL(filmURL);
            } else {
                videoView.setVisibility(View.GONE);
                if(exoPlayer!=null) {
                    exoPlayer.stop();
                }
            }
        } else {
            fullDesc.setText("");
            videoView.setVisibility(View.GONE);
            if(exoPlayer!=null) {
                exoPlayer.stop();
            }
        }
    }

    private void loadVideoURL(String url) {
        try {
            lastVideoURL = url;
            initMediaPlayer(false);
            lastTime = 0;
        }catch (Exception e){
            Log.e("StepFragment"," exoplayer error "+ e.toString());
        }
    }

    private void initMediaPlayer(boolean isOnlyResume) {
        Uri videoURI = Uri.parse(lastVideoURL);
        if(exoPlayer!=null && isOnlyResume) return;

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
        videoView.setPlayer(exoPlayer);

        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);

        exoPlayer.addListener(new ExoPlayer.EventListener() {

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch(playbackState) {
                    case ExoPlayer.STATE_BUFFERING:
                        break;
                    case ExoPlayer.STATE_ENDED:
                        exoPlayer.seekTo(0);
                        break;
                    case ExoPlayer.STATE_IDLE:
                        break;
                    case ExoPlayer.STATE_READY:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity() {

            }
        });
        if(actualStep!=null && actualStep.getId()==lastId) {
            exoPlayer.seekTo(lastTime);
        } else {
            exoPlayer.seekTo(0);
        }
        exoPlayer.setPlayWhenReady(true);//replay from start
    }

    @Override
    public void onPause() {
        super.onPause();
        lastTime = getPosition();

        if(exoPlayer!=null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(lastVideoURL==null || lastVideoURL.length()==0) return;
        initMediaPlayer(true);
    }

    public long getPosition() {
        if (exoPlayer != null)
            return exoPlayer.getCurrentPosition();
        else
            return 0;
    }

    @Override
    public void onDestroy() {
        if(exoPlayer!=null) {
            exoPlayer.stop();
        }
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_VIDEO_TIME, lastTime);
        if(actualStep!=null) {
            outState.putInt(ARG_ID_OF_STEP_ON_VIDEO, actualStep.getId());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            lastId = savedInstanceState.getInt(ARG_ID_OF_STEP_ON_VIDEO, -1);
            lastTime = savedInstanceState.getLong(ARG_VIDEO_TIME, 0);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
