/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.domain;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class StepBean {
    @SerializedName("id")
    private int id = -1;
    @SerializedName("shortDescription")
    private String shortDescription = "";
    @SerializedName("description")
    private String description = "";
    @SerializedName("videoURL")
    private String videoURL = "";
    @SerializedName("thumbnailURL")
    private String thumbnailURL = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortzDescription) {
        this.shortDescription = shortzDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @NonNull
    public String getFilmURL(){
        if(getVideoURL()!=null && getVideoURL().length()>0){
            return getVideoURL();
        } else if(getThumbnailURL()!=null && getThumbnailURL().endsWith(".mp4")){
            return getThumbnailURL();
        }
        return "";
    }
}
