/*
 * Copyright (c) 2018. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.bakingapp.domain;

import com.google.gson.annotations.SerializedName;

public class StepBean {
    @SerializedName("id")
    private int id = -1;
    @SerializedName("shortDescription")
    private String shortzDescription = "";
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

    public String getShortzDescription() {
        return shortzDescription;
    }

    public void setShortzDescription(String shortzDescription) {
        this.shortzDescription = shortzDescription;
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

}
