package com.scoutlabour.model.labour;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nkdroid on 29/3/18.
 */

public class StatusModel {

    @SerializedName("status")
    public String status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("income")
    public String income;
}
