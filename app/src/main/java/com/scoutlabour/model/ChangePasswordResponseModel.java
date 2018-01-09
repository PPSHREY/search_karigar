package com.scoutlabour.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nkdroid on 5/12/17.
 */

public class ChangePasswordResponseModel {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
}
