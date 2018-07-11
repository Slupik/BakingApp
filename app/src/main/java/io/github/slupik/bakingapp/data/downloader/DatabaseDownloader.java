/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.data.downloader;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.slupik.bakingapp.domain.RecipeBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseDownloader implements Callback<List<RecipeBean>> {
    private final DownloaderCallback mCallback;

    public DatabaseDownloader(DownloaderCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void downloadData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitDownloader udacityAPI = retrofit.create(RetrofitDownloader.class);

        Call<List<RecipeBean>> call = udacityAPI.download();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<RecipeBean>> call, Response<List<RecipeBean>> response) {
        Log.e("Retrofit", "IT IS OK");
        mCallback.onDownload(response.body());
    }

    @Override
    public void onFailure(Call<List<RecipeBean>> call, Throwable t) {
        Log.e("Retrofit", t.getMessage());
        mCallback.onDownload(new ArrayList<RecipeBean>());
    }

    public interface DownloaderCallback {
        void onDownload(List<RecipeBean> data);
    }
}
