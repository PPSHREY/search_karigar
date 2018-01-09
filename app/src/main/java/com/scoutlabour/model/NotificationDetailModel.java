package com.scoutlabour.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nkDroid Solutions on 04-Sep-17.
 */

public class NotificationDetailModel {

    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("message")
    public String message;
    @SerializedName("date")
    public String date;



}
