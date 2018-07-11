/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.data.downloader;

import java.util.List;

import io.github.slupik.bakingapp.domain.RecipeBean;
import retrofit2.Call;
import retrofit2.http.GET;

interface RetrofitDownloader {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<RecipeBean>> download();
}
